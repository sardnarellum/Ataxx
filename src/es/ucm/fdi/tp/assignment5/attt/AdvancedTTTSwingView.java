package es.ucm.fdi.tp.assignment5.attt;

import java.util.List;

import es.ucm.fdi.tp.assignment5.RectBoardSwingView;
import es.ucm.fdi.tp.basecode.attt.AdvancedTTTMove;
import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Pair;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class AdvancedTTTSwingView extends RectBoardSwingView {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Pair<Integer, Integer> currentSource;

	public AdvancedTTTSwingView(Observable<GameObserver> g, Controller c, Piece lp, Player randPlayer,
			Player aiPlayer) {
		super(g, c, lp, randPlayer, aiPlayer);
	}

	@Override
	protected void handleMouseClick(int row, int col, int mouseBtn) {
		if (1 == mouseBtn) {
			if (getBoard().getPieceCount(getTurn()) > 0) {
				Player player = new Player() {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					@Override
					public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
						return new AdvancedTTTMove(row, col, row, col, p);
					}
				};
				decideMakeManualMove(player);
			} else {
				if (null == currentSource) {
					Piece p = getBoard().getPosition(row, col);
					if (getTurn().equals(p)) {
						currentSource = new Pair<Integer, Integer>(row, col);
						addMsg("Origin: (" + currentSource.getFirst() + "," + currentSource.getSecond() + ")");
						addMsg("Click on a Destination cell");
					}
				} else {
					addMsg("Destination: (" + row + "," + col + ")");
					Player player = new Player() {
						/**
						 * 
						 */
						private static final long serialVersionUID = 1L;

						@Override
						public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
							return new AdvancedTTTMove(currentSource.getFirst(), currentSource.getSecond(), row, col,
									p);
						}
					};
					decideMakeManualMove(player);
					currentSource = null;
				}
			}
		} else if (3 == mouseBtn) {
			currentSource = null;
			addMsg("Click on an Origin cell");
		}
	}

}
