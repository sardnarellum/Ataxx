package es.ucm.fdi.tp.assignment6;

import es.ucm.fdi.tp.basecode.bgame.model.Board;

public abstract class BoardResponse implements Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final Board board;
	
	protected BoardResponse(Board board){
		this.board = board;
	}
}
