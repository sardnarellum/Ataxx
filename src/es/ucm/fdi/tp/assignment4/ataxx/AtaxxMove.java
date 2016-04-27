package es.ucm.fdi.tp.assignment4.ataxx;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

/**
 * A Class representing a move for Ataxx.
 * @author Müller András
 *
 */
public class AtaxxMove extends GameMove {
	
	private static final long serialVersionUID = 1L;	
	
	/**
	 * The row where from pick the piece returm by {@link GameMove#getPiece()}.
	 * If the distance between origin and destination fields is one, the field
	 * is left occupied by {@link GameMove#getPiece()}.
	 */
	protected int frow;	

	/**
	 * The column where from pick the piece returm by {@link GameMove#getPiece()}.
	 * If the distance between origin and destination fields is one, the field
	 * is left occupied by {@link GameMove#getPiece()}.
	 */
	protected int fcol;	

	/**
	 * The row where to place the piece returm by {@link GameMove#getPiece()}.
	 */
	protected int trow;	

	/**
	 * The row where to place the piece returm by {@link GameMove#getPiece()}.
	 */
	protected int tcol;

	/**
	 * Constructs a movement for moving a piece of the type referenced by {@code p}
	 * from position ({@code i},{@code j}) to position ({@code k},{@code l}).
	 * If the distance is only one, it places a piece of the same to the origin field.
	 * @param i Number of origin row.
	 * @param j Number of origin column.
	 * @param k Number of destination row.
	 * @param l Number of destination column.
	 * @param p A piece to bo moved or copied from ({@code i},{@code j}) to ({@code k},{@code l}).
	 */
	public AtaxxMove(int i, int j, int k, int l, Piece p) {
		super(p);
		this.frow = i;
		this.fcol = j;		
		this.trow = k;
		this.tcol = l;
	}

	public AtaxxMove() {
	}

	@Override
	public void execute(Board board, List<Piece> pieces) {		
		if (board.getPosition(trow, tcol) == null) {
			if (board.getPosition(frow, fcol) == getPiece()){
				if (distance() == 1){
					board.setPosition(trow, tcol, getPiece());
				}
				else if (distance() == 2){
					board.setPosition(trow, tcol, getPiece());
					board.setPosition(frow, fcol, null);
				}
				else {
					throw new GameError("The distance cannot be more than 2 between the origin and the destination!");
				}
				
				BoardRangeIterator it = new BoardRangeIterator(trow, tcol, 1, board);
				
				while (it.hasNext()){
					Piece p = it.next(); 
					if (null != p && pieces.contains(p) && !getPiece().equals(p)){
						it.setPosition(getPiece());
					}
				}
			}
			else {
				throw new GameError("Invalid origin position (" + frow + "," + fcol + ")!");
			}
		}
		else {
			throw new GameError("Position (" + trow + "," + tcol + ") is already occupied!");
		}
	}
	
	private int distance(){
		return Math.max(Math.abs(frow - trow), Math.abs(fcol - tcol));
	}

	@Override
	public GameMove fromString(Piece p, String str) {
		String[] words = str.split(" ");
		if (words.length != 4) {
			return null;
		}

		try {
			int frow = Integer.parseInt(words[0]);
			int fcol = Integer.parseInt(words[1]);
			int trow = Integer.parseInt(words[2]);
			int tcol = Integer.parseInt(words[3]);
			return createMove(frow, fcol, trow, tcol, p);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	/**
	 * Creates a move that is called from {@link #fromString(Piece, String)}.
	 * Separating it from that method allows us to use this class for other
	 * similar games by overriding this method.
	 * @param frow Origin row of the move being created.
	 * @param fcol Origin column of the move being created.
	 * @param trow Destination row of the move being created.
	 * @param tcol Destination column of the move being created.
	 * @param p
	 * @return
	 */
	private GameMove createMove(int frow, int fcol, int trow, int tcol, Piece p) {
		return new AtaxxMove(frow, fcol, trow, tcol, p);
	}

	@Override
	public String help() {
		return "Row and column for origin and for destination, separated by spaces (four numbers).";
	}

	@Override
	public String toString() {
		if (getPiece() == null) {
			return help();
		} else {
			return "Place a piece '" + getPiece() + "' at (" + frow + "," + fcol + ")";
		}
	}
}
