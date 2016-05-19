package es.ucm.fdi.tp.assignment5;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

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
			Player pl = new BasicPlayer(row, col);
			decideMakeManualMove(pl);
		}
	}
}
