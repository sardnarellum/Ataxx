package es.ucm.fdi.tp.assignment5;

import java.util.List;

import es.ucm.fdi.tp.assignment4.ataxx.AtaxxMove;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class TwoStepSwingPlayer extends Player {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int fromRow;
	private int fromCol;
	private int toRow;
	private int toCol;

	public void setMove(int fromRow, int fromCol, int toRow, int toCol) {
		this.fromRow = fromRow;
		this.fromCol = fromCol;
		this.toRow = toRow;
		this.toCol = toCol;
	}

	public int getFromRow() {
		return fromRow;
	}

	public int getFromCol() {
		return fromCol;
	}

	public int getToRow() {
		return toRow;
	}

	public int getToCol() {
		return toCol;
	}

	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		return new AtaxxMove(getFromRow(), getFromCol(), getToRow(), getToCol(), p);
	}
}
