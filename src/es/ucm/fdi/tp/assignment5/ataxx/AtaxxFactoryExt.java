package es.ucm.fdi.tp.assignment5.ataxx;

import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.assignment4.ataxx.AtaxxFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public class AtaxxFactoryExt extends AtaxxFactory {
	
	public AtaxxFactoryExt(int qObstacles, Integer dim) {
		super(qObstacles, dim);
	}

	public AtaxxFactoryExt(int qObstacles) {
		super(qObstacles);
	}

	@Override
	public void createSwingView(final Observable<GameObserver> g,
			final Controller c, final Piece viewPiece, Player random,
			Player ai) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				GameObserver o = new AtaxxSwingView(g, c, viewPiece, ai, ai, dim);
				g.addObserver(o);
			}			
		});
	}
}
