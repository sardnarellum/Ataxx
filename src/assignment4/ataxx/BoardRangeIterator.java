package assignment4.ataxx;

import java.util.Iterator;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Pair;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

/**
 * Class for iterating over a subset of a {@link Board}, what is
 * a range around a field.
 * @author Müller András
 *
 */
public class BoardRangeIterator implements Iterator<Piece> {
	private int nextRow;
	private final int upperBoundRow;
	private final int startCol;
	private int nextCol;
	private final int upperBoundCol;
	private final Board b;
	private int currRow;
	private int currCol;
	
	/**
	 * Constructs a new instance of the iterator.
	 * @param row The origin's row.
	 * @param col The origin's column.
	 * @param range The range of the iteration.
	 * @param board A {@link Board}.
	 */
	public BoardRangeIterator(int row, int col, int range, Board board){
		currRow = nextRow = (0 + range <= row ? row - range : 0);
		upperBoundRow = (row < board.getRows() - range ? row + range : row);
		startCol = currCol = nextCol = (0 + range < col ? col - range : 0);
		upperBoundCol = (col < board.getCols() - range ? col + range : col);
		b = board;
	}

	@Override
	public boolean hasNext() {
		return currRow < upperBoundRow || currCol < upperBoundCol;
	}

	@Override
	public Piece next() {
		return b.getPosition(
				currRow =
				nextCol == upperBoundCol
				? nextRow++
				: nextRow,
				currCol =
				nextCol == upperBoundCol
				? nextColZero()
				: nextCol++);
	}
	
	/**
	 * The coordinates of the current {@link Piece}.
	 * @return
	 */
	public Pair<Integer, Integer> getCoordinates(){
		return new Pair<Integer, Integer>(currRow, currCol);
	}
	
	/**
	 * Sets a {@link Piece} on {@link b}.
	 * @param piece The {@link Piece} to be set.
	 */
	public void setPosition(Piece piece){
		b.setPosition(currRow, currCol, piece);
	}
	
	private int nextColZero(){ // lambda function bug in eclipse
		int temp = nextCol;
		nextCol = startCol;
		return temp;
	}

}
