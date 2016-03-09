package assignment4.ataxx;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class AtaxxMove extends GameMove {
	
	private static final long serialVersionUID = 1L;	
	protected int frow;
	protected int fcol;
	protected int trow;
	protected int tcol;

	public AtaxxMove(int i, int j, int k, int l, Piece p) {
		super(p);
		this.frow = i;
		this.fcol = j;		
		this.trow = k;
		this.tcol = l;
	}

	public AtaxxMove() {
	}

	//TODO: Needs reorganize ifs
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
					if (null != p && p.getId() != "*" && getPiece() != p){
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
