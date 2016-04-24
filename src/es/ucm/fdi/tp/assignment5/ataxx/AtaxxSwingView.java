package es.ucm.fdi.tp.assignment5.ataxx;

import es.ucm.fdi.tp.assignment5.RectBoardSwingView;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Pair;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public class AtaxxSwingView extends RectBoardSwingView {

	private Pair<Integer, Integer> currentSource;

	public AtaxxSwingView(Observable<GameObserver> g, Controller c, Piece lp, Player randPlayer, Player aiPlayer) {
		super(g, c, lp, randPlayer, aiPlayer);
	}

	@Override
	protected void handleMouseClick(int row, int col, int mouseBtn) {
		if (1 == mouseBtn) {
			if (null == currentSource) {
				Piece p = getBoard().getPosition(row, col);
				if (null != p) {
					if (p.equals(getTurn())) {
						currentSource = new Pair<Integer, Integer>(row, col);
						addMsg("Origin: (" + currentSource.getFirst() + "," + currentSource.getSecond() + ")");
						addMsg("Click on a Destination cell");
					} else {
						currentSource = null;
					}
				}
			} else {
				addMsg("Destination: (" + row + "," + col + ")");
				AtaxxSwingPlayer player = new AtaxxSwingPlayer();
				player.setMove(currentSource.getFirst(), currentSource.getSecond(), row, col);
				currentSource = null;
				decideMakeManualMove(player);
			}
		} else if (3 == mouseBtn) {
			currentSource = null;
			addMsg("Click on an Origin cell");
		}
	}
}
