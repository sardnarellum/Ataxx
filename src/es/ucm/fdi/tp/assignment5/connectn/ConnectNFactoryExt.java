package es.ucm.fdi.tp.assignment5.connectn;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.connectn.ConnectNFactory;

public class ConnectNFactoryExt extends ConnectNFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ConnectNFactoryExt(int dim) {
		super(dim);
	}
	
	public ConnectNFactoryExt() {
		super();
	}

	@Override
	public void createSwingView(final Observable<GameObserver> g,
			final Controller c, final Piece viewPiece, Player random,
			Player ai) {
		// TODO Auto-generated constructor stub		
	}
}
