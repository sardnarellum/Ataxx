package es.ucm.fdi.tp.assignment5.connectn;

import es.ucm.fdi.tp.assignment5.BasicRectBoardSwingView;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class ConnectNSwingView extends BasicRectBoardSwingView {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConnectNSwingView(Observable<GameObserver> g, Controller c, Piece lp, Player randPlayer, Player aiPlayer) {
		super(g, c, lp, randPlayer, aiPlayer);
	}
}
