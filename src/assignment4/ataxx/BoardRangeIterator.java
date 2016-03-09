package assignment4.ataxx;

import java.util.Iterator;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Pair;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class BoardRangeIterator implements Iterator<Piece> {
	private int nextRow;
	private final int upperBoundRow;
	private final int startCol;
	private int nextCol;
	private final int upperBoundCol;
	private final Board b;
	private int currRow;
	private int currCol;
	
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
	
	public Pair<Integer, Integer> getCoordinates(){
		return new Pair<Integer, Integer>(currRow, currCol);
	}
	
	public void setPosition(Piece piece){
		b.setPosition(currRow, currCol, piece);
	}
	
	private int nextColZero(){ // lambda function bug in eclipse
		int temp = nextCol;
		nextCol = startCol;
		return temp;
	}

}
