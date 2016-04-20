package es.ucm.fdi.tp.assignment5.ataxx;

import es.ucm.fdi.tp.assignment5.RectBoardSwingView;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class AtaxxSwingView extends RectBoardSwingView {

	public AtaxxSwingView(Observable<GameObserver> g, Controller c, Piece lp, Player randPlayer, Player aiPlayer, int dim) {
		super(g, c, lp, randPlayer, aiPlayer, dim);
		// TODO Auto-generated constructor stub
	}
}
