package assignment.ataxx;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class AtaxxMove extends GameMove {
	
	private static final long serialVersionUID = 1L;	
	protected int row;
	protected int col;

	public AtaxxMove(int i, int j, Piece p) {
		super(p);
		this.row = i;
		this.col = j;		
	}

	public AtaxxMove() {
	}

	@Override
	public void execute(Board board, List<Piece> pieces) {
		if (board.getPosition(row, col) == null) {
			board.setPosition(row, col, getPiece());
		} else {
			throw new GameError("position (" + row + "," + col + ") is already occupied!");
		}
	}

	@Override
	public GameMove fromString(Piece p, String str) {
		String[] words = str.split(" ");
		if (words.length != 2) {
			return null;
		}

		try {
			int row, col;
			row = Integer.parseInt(words[0]);
			col = Integer.parseInt(words[1]);
			return createMove(row, col, p);
		} catch (NumberFormatException e) {
			return null;
		}
	}

	private GameMove createMove(int row, int col, Piece p) {
		return new AtaxxMove(row, col, p);
	}

	@Override
	public String help() {
		return "'row column', to place a piece at the corresponding position.";
	}

	@Override
	public String toString() {
		if (getPiece() == null) {
			return help();
		} else {
			return "Place a piece '" + getPiece() + "' at (" + row + "," + col + ")";
		}
	}
}
