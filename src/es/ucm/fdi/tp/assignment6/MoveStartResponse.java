package es.ucm.fdi.tp.assignment6;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class MoveStartResponse extends BoardTurnResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected MoveStartResponse(Board board, Piece turn) {
		super(board, turn);
	}

	@Override
	public void run(GameObserver o) {
		o.onMoveStart(board, turn);
	}

}
