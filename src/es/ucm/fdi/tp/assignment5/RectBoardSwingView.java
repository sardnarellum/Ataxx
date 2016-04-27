package es.ucm.fdi.tp.assignment5;

import java.awt.Color;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;
import es.ucm.fdi.tp.basecode.bgame.model.Observable;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public abstract class RectBoardSwingView extends SwingView {

	private BoardComponent boardComp;

	public RectBoardSwingView(Observable<GameObserver> g, Controller c, Piece lp, Player randPlayer, Player aiPlayer) {
		super(g, c, lp, randPlayer, aiPlayer);
	}

	@Override
	protected void initBoardGUI() {
		boardComp = new BoardComponent() {

			@Override
			protected Color getPieceColor(Piece p) {
				return RectBoardSwingView.this.getPieceColor(p);
			}

			@Override
			protected boolean isPlayerPiece(Piece p) {
				return getPieces().contains(p);
			}

			@Override
			protected void mouseClick(int row, int col, int mouseBtn) {
				handleMouseClick(row, col, mouseBtn);
			}
		};
		setBoardArea(boardComp);
	}

	@Override
	protected void activateBoard() {
		boardComp.setEnabled(true);
	}

	@Override
	protected void deActivateBoard() {
		boardComp.setEnabled(false);
	}

	@Override
	protected void redrawBoard() {
		boardComp.redraw(getBoard());
	}

	protected abstract void handleMouseClick(int row, int col, int mouseBtn);
}
