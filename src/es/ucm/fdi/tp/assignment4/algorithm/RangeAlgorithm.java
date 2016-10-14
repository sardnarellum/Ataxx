package es.ucm.fdi.tp.assignment4.algorithm;

import java.util.ArrayList;

import es.ucm.fdi.tp.assignment4.ataxx.BoardRangeIterator;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Pair;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class RangeAlgorithm implements BoardAlgorithm {
	final int range;

	public RangeAlgorithm(int range) {
		this.range = range;
	}

	@Override
	public boolean pieceCanMove(Board board, Piece piece) {
		for (int i = 0; i < board.getRows(); ++i) {
			for (int j = 0; j < board.getCols(); ++j) {
				if (board.getPosition(i, j) == piece && emptyPlaceInRange(board, i, j)) {
					return true;
				}
			}
		}

		return false;
	}
	
	public ArrayList<Pair<Integer, Integer>> emptyPlacesInRange(
			Board board, int row, int col) {
		ArrayList<Pair<Integer, Integer>> places = new ArrayList<Pair<Integer, Integer>>();
		BoardRangeIterator it = new BoardRangeIterator(row, col, 2, board);

		while (it.hasNext()) {
			if (it.next() == null) {
				places.add(it.getCoordinates());
			}
		}

		return places;
	}

	public boolean emptyPlaceInRange(Board board, int row, int col) {
		BoardRangeIterator it = new BoardRangeIterator(row, col, range, board);

		while (it.hasNext()) {
			if (null == it.next()) {
				return true;
			}
		}

		return false;
	}
}
