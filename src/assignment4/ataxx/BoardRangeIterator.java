package assignment4.ataxx;

import java.util.Iterator;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

// TODO: Az utolsó elem kimarad.
public class BoardRangeIterator implements Iterator<Piece> {
	private int nextRow;
	private final int upperBoundRow;
	private final int startCol;
	private int nextCol;
	private final int upperBoundCol;
	private final Board b;
	private int currRow;
	private int currCol;
	
	public BoardRangeIterator(int row, int col, int dist, Board board){
		currRow = nextRow = (0 + dist <= row ? row - dist : 0);
		upperBoundRow = (row < board.getRows() - dist ? row + dist : row);
		startCol = currCol = nextCol = (0 + dist < col ? col - dist : 0);
		upperBoundCol = (col < board.getCols() - dist ? col + dist : col);
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
				? currZero()
				: nextCol++);
	}
	
	public void setPosition(Piece piece){
		b.setPosition(currRow, currCol, piece);
	}
	
	private int currZero(){
		int temp = nextCol;
		nextCol = startCol;
		return temp;
	}

}
