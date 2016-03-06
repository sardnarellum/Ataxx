package assignment4.ataxx;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import es.ucm.fdi.tp.basecode.bgame.control.ConsolePlayer;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.DummyAIPlayer;
import es.ucm.fdi.tp.basecode.bgame.control.GameFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.AIAlgorithm;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.bgame.views.GenericConsoleView;

public class AtaxxFactory implements GameFactory {
	
	protected int dim;
	
	public AtaxxFactory(){
		this(5);
	}
	
	public AtaxxFactory(int dim){		
		this.dim = dim;
	}

	@Override
	public GameRules gameRules() {
		return new AtaxxRules(dim);
	}

	@Override
	public Player createConsolePlayer() {
		ArrayList<GameMove> possibleMoves = new ArrayList<GameMove>();
		possibleMoves.add(new AtaxxMove());
		
		return new ConsolePlayer(new Scanner(System.in), possibleMoves);
	}

	@Override
	public Player createRandomPlayer() {
		return new AtaxxRandomPlayer();
	}

	@Override
	public Player createAIPlayer(AIAlgorithm alg) {
		return new DummyAIPlayer(createRandomPlayer(), 1000);
	}

	/**
	 * By default, we have two players, X and O.
	 */
	@Override
	public List<Piece> createDefaultPieces() {
		List<Piece> pieces = new ArrayList<Piece>();
		pieces.add(new Piece("X"));
		pieces.add(new Piece("O"));
		
		return pieces;
	}

	@Override
	public void createConsoleView(Observable<GameObserver> game, Controller ctrl) {
		new GenericConsoleView(game, ctrl);		
	}

	@Override
	public void createSwingView(Observable<GameObserver> game, Controller ctrl, Piece viewPiece, Player randPlayer,
			Player aiPlayer) {
		throw new UnsupportedOperationException("There is no swing view");		
	}

}
