package es.ucm.fdi.tp.assignment6;

import java.io.IOException;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.control.commands.Command;
import es.ucm.fdi.tp.basecode.bgame.control.commands.PlayCommand;
import es.ucm.fdi.tp.basecode.bgame.control.commands.QuitCommand;
import es.ucm.fdi.tp.basecode.bgame.control.commands.RestartCommand;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;

public class GameClient extends Controller implements Observable<GameObserver>, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String host;
	private final int port;
	private Piece localPiece;
	private List<GameObserver> obs;
	private GameFactory gameFactory;
	private Connection server;
	private boolean gameOver;

	public GameClient(String host, int port) throws Exception {
		super(null, null);
		this.host = host;
		this.port = port;
		this.gameOver = false;
		this.obs = new ArrayList<GameObserver>();
		connect();
	}

	public GameFactory getGameFactory() {
		return gameFactory;
	}

	public Piece getPlayerPiece() {
		return localPiece;
	}

	public void start() {
		this.obs.add(new GameObserver() {

			@Override
			public void onMoveStart(Board board, Piece turn) {
			}

			@Override
			public void onMoveEnd(Board board, Piece turn, boolean success) {
			}

			@Override
			public void onGameStart(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
			}

			@Override
			public void onGameOver(Board board, State state, Piece winner) {
				gameOver = true;
				try {
					server.stop();
				} catch (IOException e) {
				}
			}

			@Override
			public void onError(String msg) {
			}

			@Override
			public void onChangeTurn(Board board, Piece turn) {
			}
		});

		while (!gameOver) {
			try {
				Response res = (Response) server.getObject();
				for (GameObserver o : obs) {
					res.run(o);
				}
			} catch (ClassNotFoundException | IOException e) {
				throw new GameError("An exception occured while handling a response: " + e.getMessage());
			}
		}
	}

	@Override
	public void addObserver(GameObserver o) {
		obs.add(o);
	}

	@Override
	public void removeObserver(GameObserver o) {
		obs.remove(o);
	}

	@Override
	public void makeMove(Player p) {
		forwardCommand(new PlayCommand(p));
	}

	@Override
	public void stop() {
		forwardCommand(new QuitCommand());
	}

	@Override
	public void restart() {
		forwardCommand(new RestartCommand());
	}

	private void forwardCommand(Command command) {
		if (!gameOver) {
			try {
				server.sendObject(command);
			} catch (IOException e) {
			}
		}
	}

	private void connect() throws Exception {
		server = new Connection(new Socket(host, port));

		server.sendObject("Connect");
		Object response = server.getObject();

		if (response instanceof Exception) {
			throw (Exception) response;
		}

		try {
			gameFactory = (GameFactory) server.getObject();
			localPiece = (Piece) server.getObject();
		} catch (Exception e) {
			throw new GameError("Unknown server response: " + e.getMessage());
		}
	}
}
