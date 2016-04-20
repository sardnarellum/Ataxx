package es.ucm.fdi.tp.assignment5;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public class RectBoardSwingView extends SwingView {
	
	private int dim;
	BoardComponent boardComp;

	public RectBoardSwingView(Observable<GameObserver> g, Controller c,
			Piece lp, Player randPlayer, Player aiPlayer, int size) {
		super(g, c, lp, randPlayer, aiPlayer);
		this.dim = size;
	}

	@Override
	protected void initBoardGUI() {
		boardComp = new BoardComponent(5, 5);
		setBoardArea(boardComp);
	}

	@Override
	protected void activateBoard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void deActivateBoard() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void redrawBoard() {
		// TODO Auto-generated method stub
		
	}
}
