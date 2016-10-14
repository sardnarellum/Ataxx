package es.ucm.fdi.tp.assignment4.board;

import es.ucm.fdi.tp.basecode.bgame.model.FiniteRectBoard;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class QuadrantBoard extends FiniteRectBoard {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public QuadrantBoard(int rows, int cols) {
		super(rows, cols);
	}

	public void setInAllQuadrants(int row, int col, Piece p) {
		setPosition(row, col, p);
		setPosition(getRows() - row - 1, col, p);
		setPosition(row, getCols() - col - 1, p);
		setPosition(getRows() - row - 1, getCols() - col - 1, p);
	}
}
