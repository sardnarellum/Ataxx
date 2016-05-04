package es.ucm.fdi.tp.assignment6;

import java.net.ServerSocket;
import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class GameServer extends Controller implements GameObserver {

	private final int port;
	private final int numPlayers;
	private int numClients;
	private GameFactory gameFactory;
	private List<Connection> clients;
	
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
	public void onGameStart(Board board, String gameDesc, List<Piece> pieces,
			Piece turn) {
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

}
