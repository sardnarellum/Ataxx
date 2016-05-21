package es.ucm.fdi.tp.assignment6;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public abstract class BoardTurnResponse extends BoardResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final Piece turn;

	protected BoardTurnResponse(Board board, Piece turn) {
		super(board);
		this.turn = turn;
	}
}
