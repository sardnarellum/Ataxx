package es.ucm.fdi.tp.assignment6;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.control.commands.Command;
import es.ucm.fdi.tp.basecode.bgame.control.commands.PlayCommand;
import es.ucm.fdi.tp.basecode.bgame.control.commands.QuitCommand;
import es.ucm.fdi.tp.basecode.bgame.control.commands.RestartCommand;
import es.ucm.fdi.tp.basecode.bgame.model.Game;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class GameClient extends Controller implements Observable<GameObserver> {

	private final String host;
	private final int port;
	private Piece localPiece;
	private List<GameObserver> obs;
	private GameFactory gameFactory;
	private Connection server;
	private boolean gameOver;

	public GameClient(String host, int port) throws UnknownHostException, IOException {
		super(null, null);
		this.host = host;
		this.port = port;
		this.gameOver = false;
		connect();
	}

	public GameFactory getGameFactory() {
		return gameFactory;
	}

	public Piece getPlayerPiece() {
		return localPiece;
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
	public void makeMove(Player p){
		forwardCommand(new PlayCommand(p));
	}
	
	@Override
	public void stop(){
		forwardCommand(new QuitCommand());
	}
	
	public void restart(){
		forwardCommand(new RestartCommand());
	}

	private void forwardCommand(Command command) {
		if (!gameOver){
			try {
				server.sendObject(command);
			} catch (IOException e) {
			}
		}
	}

	private void connect() throws UnknownHostException, IOException {
		server = new Connection(new Socket(host, port));
	}
}
