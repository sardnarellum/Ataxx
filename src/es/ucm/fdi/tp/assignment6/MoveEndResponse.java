package es.ucm.fdi.tp.assignment6;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class MoveEndResponse extends BoardTurnResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private boolean success;

	public MoveEndResponse(Board board, Piece turn, boolean success) {
		super(board, turn);
		this.success = success;
	}

	@Override
	public void run(GameObserver o) {
		o.onMoveEnd(board, turn, success);
	}

}
