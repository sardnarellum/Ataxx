package es.ucm.fdi.tp.assignment5;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.connectn.ConnectNMove;

public class BasicRectBoardSwingView extends RectBoardSwingView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BasicRectBoardSwingView(Observable<GameObserver> g, Controller c, Piece lp, Player randPlayer,
			Player aiPlayer) {
		super(g, c, lp, randPlayer, aiPlayer);
	}

	@Override
	protected void handleMouseClick(int row, int col, int mouseBtn) {
		if (1 == mouseBtn) {
			Player pl = new Player() {

				/**
				 * 
				 */
				private static final long serialVersionUID = 1L;

				@Override
				public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
					return new ConnectNMove(row, col, p);
				}

			};
			decideMakeManualMove(pl);
		}
	}
}
