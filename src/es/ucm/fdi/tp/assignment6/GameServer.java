package es.ucm.fdi.tp.assignment6;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class GameServer extends Controller implements GameObserver {

	private final int port;
	private final int numPlayers;
	private int numClients;
	private GameFactory gameFactory;
	private List<Connection> clients;
	private TimedLogArea logArea;

	volatile private ServerSocket server;
	volatile private boolean stopped;
	volatile private boolean gameOver;

	public GameServer(GameFactory gameFactory, List<Piece> pieces, int port) {
		super(new Game(gameFactory.gameRules()), pieces);
		this.port = port;
		this.numPlayers = pieces.size();
		this.gameFactory = gameFactory;

		game.addObserver(this);
	}

	@Override
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGameOver(Board board, State state, Piece winner) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMoveStart(Board board, Piece turn) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMoveEnd(Board board, Piece turn, boolean success) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onChangeTurn(Board board, Piece turn) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onError(String msg) {
		// TODO Auto-generated method stub

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
		try {
			super.stop();
		} catch (GameError e) {
		} finally {
			try {
				server.close();
			} catch (IOException e) {
			}
		}
	}

	@Override
	public synchronized void restart() {
		try {
			super.restart();
		} catch (GameError e) {
		}
	}

	@Override
	public void start() {
		controlGUI();
		try {
			startServer();
		} catch (IOException e) {
			log("An error has occured with the starting of the server: " + e.getMessage());
		}
	}

	public int clientCount() {
		return clients != null ? clients.size() : 0;
	}

	private void controlGUI() {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					constructGUI();
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
			throw new GameError("Something went wrong with the construction of the GUI.");
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

		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout(5, 5));
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPanel.add(logPanel, BorderLayout.CENTER);
		contentPanel.add(stopBtn, BorderLayout.PAGE_END);
		contentPanel.setOpaque(true);

		JFrame window = new JFrame("Game Server");
		window.setContentPane(contentPanel);
		window.setPreferredSize(new Dimension(300, 300));
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		window.pack();
		window.setVisible(true);
	}

	private void log(String msg) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				logArea.addLine(msg);
			}
		});
	}

	private void startServer() throws IOException {
		try {
			server = new ServerSocket(port);
			clients = new ArrayList<Connection>();
			stopped = false;
			log("Waiting for players...");
			while (!stopped) {
				Socket s = server.accept();
				handleRequest(s);
			}
		} catch (IOException e) {
			throw e;
		} finally {
			stop();
		}
	}

	private void handleRequest(Socket s) {
		try {
			Connection c = new Connection(s);
			Object clientRequest = c.getObject();
			if (!(clientRequest instanceof String) || !((String) clientRequest).equalsIgnoreCase("Connect")) {
				log("[ERROR] Invalid request from " + s.getPort());
				c.sendObject(new GameError("Invalid Request"));
				c.stop();
				return;
			}

			if (!(clientCount() < numPlayers)) {
				c.sendObject(new GameError("All slots are occupied."));
				log("[ERROR] " + s.getPort() + " tried to connect, but all slots are occupied.");
				return;
			}

			clients.add(c);
			c.sendObject("OK");
			c.sendObject(gameFactory);
			c.sendObject(pieces.get(clients.indexOf(c)));
			log("[NEW CLIENT] " + s.getPort() + " accepted.");

			if (clientCount() == numPlayers) {
				// TODO start game
			}
		} catch (IOException | ClassNotFoundException e) {
		}
	}
}
