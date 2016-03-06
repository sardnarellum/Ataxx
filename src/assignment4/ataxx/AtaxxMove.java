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
				
				for (int i = 0 < trow ? trow - 1 : 0;
				         i < (trow < board.getRows() - 1 ? trow + 1 : trow);
				         ++i){			
					for (int j = 0 < tcol ? tcol - 1 : 0;
					         j < (trow < board.getCols() - 1 ? tcol + 1 : tcol);
							 ++j){
						Piece p = board.getPosition(i, j); 
						if (null != p && getPiece() != p){
							board.setPosition(i, j, getPiece());
						}
					}
				}
			}
			else {
				throw new GameError("invalid origin position (" + frow + "," + fcol + ")!");
			}
		} else {
			throw new GameError("position (" + trow + "," + tcol + ") is already occupied!");
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
