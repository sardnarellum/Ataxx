package es.ucm.fdi.tp.assignment6;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class GameStartResponse extends BoardResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String gameDesc;
	private List<Piece> pieces;
	private Piece turn;

	public GameStartResponse(Board board, String gameDesc, List<Piece> pieces, Piece turn) {
		super(board);
		this.gameDesc = gameDesc;
		this.pieces = pieces;
		this.turn = turn;
	}

	@Override
	public void run(GameObserver o) {
		o.onGameStart(board, gameDesc, pieces, turn);
	}

}
