package es.ucm.fdi.tp.assignment5.ataxx;

import java.lang.reflect.InvocationTargetException;

import javax.swing.SwingUtilities;

import es.ucm.fdi.tp.assignment4.ataxx.AtaxxFactory;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class AtaxxFactoryExt extends AtaxxFactory {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public AtaxxFactoryExt(int qObstacles, Integer dim) {
		super(qObstacles, dim);
	}

	public AtaxxFactoryExt(int qObstacles) {
		super(qObstacles);
	}

	@Override
	public void createSwingView(final Observable<GameObserver> g, final Controller c, final Piece viewPiece,
			Player random, Player ai) {
		try {
			SwingUtilities.invokeAndWait(new Runnable() {

				@Override
				public void run() {
					GameObserver o = new AtaxxSwingView(g, c, viewPiece, random, ai);
					g.addObserver(o);
				}
			});
		} catch (InvocationTargetException | InterruptedException e) {
		}
	}
}
