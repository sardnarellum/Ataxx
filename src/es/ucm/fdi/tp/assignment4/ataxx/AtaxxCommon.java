package es.ucm.fdi.tp.assignment4.ataxx;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Pair;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

/**
 * Static methods for common and usually tasks.
 * @author Müller András
 *
 */
public final class AtaxxCommon {

	/**
	 * Determines that an empty field exists in the two fields distance around
	 * a field of a {@link Board}.
	 * @param board
	 * @param row
	 * @param col
	 * @return
	 */
	public static boolean emptyPlaceInRange(Board board, int row, int col) {
		BoardRangeIterator it = new BoardRangeIterator(row, col, 2, board);
		
		while (it.hasNext()){
			if (null == it.next()){
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Determines that a player can move or not.
	 * @param board
	 * @param piece
	 * @return
	 */
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
	
	/**
	 * Determines if at least one {@link Piece} can be moved on a {@link Board}.
	 * @param board The gameboard used for Ataxx.
	 * @param pieces List of pieces.
	 * @return True if at least one piece can be moved.
	 */
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

	/**
	 * Counts the number of the same pieces with a given {@link Piece} on a {@link Board}.
	 * @param board The board to search on.
	 * @param piece The piece to be searched.
	 * @return The number of occurences of {@code piece} on {@code board}.
	 */
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
	
	/**
	 * Finds and returns the coordinates of the empty fields in the distance of two fiedls
	 * around the field referenced by ({@code row},{@code col}).
	 * @param board
	 * @param row The origin's row.
	 * @param col The origin's column.
	 * @return The list of the empty fields' coordinates.
	 */
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
	
	/**
	 * Finds and returns the empty places' coordinates of a board.
	 * @param board
	 * @return The list of the coordinates.
	 */
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
	
	/**
	 * Sets four pieces on a board mirrored to the axles.
	 * @param row number of the new piece's row in the upper left quadrant.
	 * @param col number of the new piece's column in the upper left quadrant.
	 * @param b the {@link Board} to be modified.
	 * @param p the {@link Piece} to be inserted.
	 */
	public static void setInAllQuadrants(int row, int col, Board b, Piece p){
		b.setPosition(row, col, p);
		b.setPosition(b.getRows() - row - 1, col, p);
		b.setPosition(row, b.getCols() - col - 1, p);
		b.setPosition(b.getRows() - row - 1, b.getCols() - col - 1, p);
	}
}
