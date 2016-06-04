package es.ucm.fdi.tp.assignment4.algorithm;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Pair;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public interface BoardAlgorithm {

	boolean pieceCanMove(Board board, Piece piece);

	default boolean anyPieceMovable(Board board, List<Piece> pieces) {
		if (!board.isFull()) {
			for (Piece e : pieces) {
				if (pieceCanMove(board, e)) {
					return true;
				}
			}
		}

		return false;
	}

	default int pieceOnBoardCount(Board board, Piece piece) {
		int c = 0;

		for (int i = 0; i < board.getRows(); ++i) {
			for (int j = 0; j < board.getCols(); ++j) {
				if (board.getPosition(i, j) == piece) {
					++c;
				}
			}
		}

		return c;
	}

	default Piece onlyPieceOnBoard(Board board, List<Piece> pieces) {
		Piece p = null;

		for (int i = 0; i < board.getRows(); ++i) {
			for (int j = 0; j < board.getCols(); ++j) {
				Piece currPiece = board.getPosition(i, j);
				if (null != currPiece && pieces.contains(currPiece)) {
					if (null == p) {
						p = board.getPosition(i, j);
					} else {
						if (p != currPiece) {
							return null;
						}
					}
				}
			}
		}

		return p;
	}

	default ArrayList<Pair<Integer, Integer>> emptyPlacesQuadrant(Board board) {
		ArrayList<Pair<Integer, Integer>> places = new ArrayList<Pair<Integer, Integer>>();

		for (int i = 0; i < board.getRows() / 2; ++i) {
			for (int j = 0; j < board.getCols() / 2; ++j) {
				if (board.getPosition(i, j) == null)
					places.add(new Pair<Integer, Integer>(i, j));
			}
		}

		return places;
	}

	default void setInAllQuadrants(int row, int col, Board b, Piece p) {
		b.setPosition(row, col, p);
		b.setPosition(b.getRows() - row - 1, col, p);
		b.setPosition(row, b.getCols() - col - 1, p);
		b.setPosition(b.getRows() - row - 1, b.getCols() - col - 1, p);
	}
}
