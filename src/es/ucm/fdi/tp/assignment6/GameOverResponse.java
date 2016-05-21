package es.ucm.fdi.tp.assignment6;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class GameOverResponse extends BoardResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private State state;
	private Piece winner;

	public GameOverResponse(Board board, State state, Piece winner) {
		super(board);
		this.state = state;
		this.winner = winner;
	}

	@Override
	public void run(GameObserver o) {
		o.onGameOver(board, state, winner);
	}

}
