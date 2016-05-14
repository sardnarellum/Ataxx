package es.ucm.fdi.tp.assignment6;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class ChangeTurnResponse extends BoardTurnResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected ChangeTurnResponse(Board board, Piece turn) {
		super(board, turn);
	}

	@Override
	public void run(GameObserver o) {
		o.onChangeTurn(board, turn);
	}

}
