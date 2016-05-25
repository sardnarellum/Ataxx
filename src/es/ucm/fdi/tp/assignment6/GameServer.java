package es.ucm.fdi.tp.assignment6;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.control.commands.Command;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class GameServer extends Controller implements GameObserver {

	private final int port;
	private final int numPlayers;
	private GameFactory gameFactory;
	private TimedLogArea logArea;

	volatile private List<Connection> clients;
	volatile private ServerSocket server;
	volatile private boolean stopped;
	volatile private boolean gameOver;
	volatile private boolean firstRun;

	public GameServer(GameFactory gameFactory, List<Piece> pieces, int port) {
		super(new Game(gameFactory.gameRules()), pieces);
		this.port = port;
		this.numPlayers = pieces.size();
		this.gameFactory = gameFactory;
		this.firstRun = true;

		game.addObserver(this);
	}

	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		forwardNotification(new GameStartResponse(board, gameDesc, pieces, turn));
	}

	@Override
	public void onGameOver(Board board, State state, Piece winner) {
		forwardNotification(new GameOverResponse(board, state, winner));
		stop();
	}

	@Override
	public void onMoveStart(Board board, Piece turn) {
		forwardNotification(new MoveStartResponse(board, turn));
	}

	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {
		forwardNotification(new MoveEndResponse(board, turn, success));
	}

	@Override
	public void onChangeTurn(Board board, Piece turn) {
		forwardNotification(new ChangeTurnResponse(board, turn));
	}

	@Override
	public void onError(String msg) {
		forwardNotification(new ErrorResponse(msg));
	}

	@Override
	public synchronized void makeMove(Player player) {
		try {
			super.makeMove(player);
		} catch (GameError e) {
		}
	}

	@Override
	public synchronized void stop() {
		if (State.InPlay == game.getState()) {
			super.stop();
		}
		gameOver = true;
		for (Connection c : clients) {
			try {
				c.stop();
				log("[Disconnect] " + c);
			} catch (IOException e) {
				logError("An error occured when tried to disconnect client " + c + ".");
			}
		}
		clients.clear();
	}

	@Override
	public synchronized void restart() {
		try {
			super.restart();
		} catch (GameError e) {
			logError("Unable to restart properly: " + e.getMessage());
		}
	}

	@Override
	public void start() {
		try {
			initGUI();
			startServer();
		} catch (GameError | IOException e) {
			logError("An error has occured when tried to start the server: " + e.getMessage());
			System.exit(1);
		}
	}

	private int clientCount() {
		return clients != null ? clients.size() : 0;
	}

	private void initGUI() throws RuntimeException {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {
				@Override
				public void run() {
					constructGUI();
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			throw new RuntimeException("Something went wrong with the construction of the GUI: " + e.getMessage());
		}
	}

	protected void constructGUI() {
		logArea = new TimedLogArea();
		logArea.setEditable(false);
		logArea.setLineWrap(true);
		logArea.setWrapStyleWord(true);

		JScrollPane logScrollPane = new JScrollPane(logArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		logScrollPane.setPreferredSize(new Dimension(300, 300));

		JPanel logPanel = new JPanel();
		logPanel.setLayout(new BorderLayout());
		logPanel.add(logScrollPane);

		JButton stopBtn = new JButton("Stop");
		stopBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int status = 0;
				try {
					stopServer();
				} catch (GameError ex) {
					status = 1;
					logError(ex.getMessage());
				} finally {
					System.exit(status);
				}
			}
		});

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout(5, 5));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.add(logPanel, BorderLayout.CENTER);
		contentPanel.add(stopBtn, BorderLayout.PAGE_END);
		contentPanel.setOpaque(true);

		JFrame window = new JFrame("Game Server");
		window.setContentPane(contentPanel);
		window.setPreferredSize(new Dimension(600, 300));
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		window.pack();
		window.setVisible(true);
	}

	private void startServer() throws IOException {
		try {
			server = new ServerSocket(port);
			clients = new ArrayList<Connection>();
			stopped = false;
			log("[START] Waiting for a connections...");
			while (!stopped) {
				Socket s = server.accept();
				handleRequest(s);
			}
		} catch (IOException e) {
			if (!stopped) {
				throw new IOException("Error while waiting for a connection: " + e.getMessage());
			}
		}
	}

	private void handleRequest(Socket s) {
		try {
			Connection c = new Connection(s);
			Object clientRequest = c.getObject();
			if (!(clientRequest instanceof String) || !((String) clientRequest).equalsIgnoreCase("Connect")) {
				logError("Invalid request from " + c);
				c.sendObject(new GameError("Invalid Request"));
				c.stop();
				return;
			}

			if (!(clientCount() < numPlayers)) {
				c.sendObject(new GameError("All slots are occupied."));
				logError(c + " tried to connect, but all slots are occupied.");
				return;
			}

			clients.add(c);
			c.sendObject("OK");
			c.sendObject(gameFactory);
			c.sendObject(pieces.get(clients.indexOf(c)));
			log("[New client] " + c + " accepted.");

			if (clientCount() == numPlayers) {
				if (firstRun) {
					super.start();
					firstRun = false;
				} else {
					restart();
				}
			}

			startClientListener(c);
		} catch (IOException | ClassNotFoundException e) {
			logError("An error occured when tried to handle a request: " + e.getMessage());
		}
	}

	private void startClientListener(Connection c) {
		gameOver = false;
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!stopped && !gameOver) {
					try {
						Command cmd = (Command) c.getObject();
						log("[" + c + "] " + cmd.helpText());
						cmd.execute(GameServer.this);
					} catch (ClassNotFoundException | IOException e) {
						if (!stopped && !gameOver) {
							logError("Unable to process command: " + e.getMessage());
							stop();
						}
					} catch (Exception e) {
						logError("Undexpected error: " + e.getMessage());
					}
				}
			}
		});
		t.start();
	}

	private void stopServer() throws GameError {
		stopped = true;
		stop();
		try {
			server.close();
			log("[STOP] Server shutdown.");
		} catch (IOException e) {
			throw new GameError("An error occured when tried to stop the server: " + e.getMessage());
		}
	}

	private void forwardNotification(Response response) {
		log("[FWN] " + response.getClass().getSimpleName());
		for (Connection c : clients) {
			try {
				c.sendObject(response);
			} catch (IOException e) {
				logError("Unable to send response to: " + c);
			}
		}
	}

	private void log(String msg) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				logArea.addLine(msg);
			}
		});
	}

	private void logError(String msg) {
		log("[Error] " + msg);
	}
}
