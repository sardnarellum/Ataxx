package assignment4.ataxx;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Pair;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public final class AtaxxCommon {

	public static boolean emptyPlaceInRange(Board board, int row, int col) {
		BoardRangeIterator it = new BoardRangeIterator(row, col, 2, board);
		
		while (it.hasNext()){
			if (null == it.next()){
				return true;
			}
		}
		
		return false;
	}
	
	public static boolean playerCanMove(Board board, Piece piece){		
		for (int i = 0; i < board.getRows(); ++i){
			for (int j = 0; j < board.getCols(); ++j){
				if (board.getPosition(i, j) == piece
						&& emptyPlaceInRange(board, i, j)){
					return true;
				}
			}
		}
		
		return false;
	}
	
	public static boolean gameCanContinue(Board board, List<Piece> pieces){
		if (!board.isFull()){
			for (Piece e : pieces){
				if (playerCanMove(board, e)){
					return true;
				}
			}
		}
		
		return false;
	}

	public static int pieceOnBoardCount(Board board, Piece piece){
		int c = 0;
		
		for (int i = 0; i < board.getRows(); ++i){
			for (int j = 0; j < board.getCols(); ++j){
				if (board.getPosition(i, j) == piece){
					++c;
				}
			}
		}
		
		return c;
	}
	
	public static ArrayList<Pair<Integer, Integer>> emptyPlacesInRange(
			Board board, int row, int col){
		ArrayList<Pair<Integer, Integer>> places = new ArrayList<Pair<Integer, Integer>>();
		BoardRangeIterator it = new BoardRangeIterator(row, col, 2, board);
		
		while (it.hasNext()){			
			if (it.next() == null){
				places.add(it.getCoordinates());
			}
		}
		
		return places;
	}
	
	public static ArrayList<Pair<Integer, Integer>> emptyPlacesQuadrant(Board board){
		ArrayList<Pair<Integer, Integer>> places = new ArrayList<Pair<Integer, Integer>>(); // Why typedef doesn't exist in java?????!!
		
		for (int i = 0; i < board.getRows() / 2; ++i){
			for (int j = 0; j < board.getCols() / 2; ++j){
				if (board.getPosition(i, j) == null)
					places.add(new Pair<Integer, Integer>(i, j)); 
			}
		}
		
		return places;
	}
	
	public static void setInAllQuadrants(int row, int col, Board b, Piece p){
		b.setPosition(row, col, p);
		b.setPosition(b.getRows() - row - 1, col, p);
		b.setPosition(row, b.getCols() - col - 1, p);
		b.setPosition(b.getRows() - row - 1, b.getCols() - col - 1, p);
	}
}
