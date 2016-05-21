package es.ucm.fdi.tp.assignment5;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public abstract class BoardComponent extends JComponent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int _CELL_HEIGHT = 50;
	private int _CELL_WIDTH = 50;
	private boolean enabled;

	private Board board;

	public BoardComponent() {
		enabled = true;
		initGUI();
	}

	private void initGUI() {
		addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (enabled) {
					mouseClick(e.getX() / _CELL_WIDTH, e.getY() / _CELL_HEIGHT, e.getButton());
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (null != board) {
			_CELL_WIDTH = this.getWidth() / getCols();
			_CELL_HEIGHT = this.getHeight() / getRows();

			for (int i = 0; i < getRows(); i++)
				for (int j = 0; j < getCols(); j++)
					drawCell(i, j, board.getPosition(j, i), g);
		}
	}

	private void drawCell(int row, int col, Piece p, Graphics g) {
		int x = col * _CELL_WIDTH;
		int y = row * _CELL_HEIGHT;

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x + 2, y + 2, _CELL_WIDTH - 4, _CELL_HEIGHT - 4);

		if (isPlayerPiece(p)) {
			g.setColor(getPieceColor(p));
			g.fillOval(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);
			g.setColor(Color.black);
			g.drawOval(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);
		} else if (null != p) {
			g.setColor(Color.darkGray);
			g.fillRect(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);
			g.setColor(Color.black);
			g.drawRect(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);
		}

	}

	public void setBoardSize(int rows, int cols) {
		repaint();
	}

	public int getRows() {
		return null != board ? board.getRows() : 0;
	}

	public int getCols() {
		return null != board ? board.getCols() : 0;
	}

	public void redraw(Board board) {
		this.board = board;
		repaint();
	}

	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		this.enabled = enabled;
	}

	protected abstract Color getPieceColor(Piece p);

	protected abstract boolean isPlayerPiece(Piece p);

	protected abstract void mouseClick(int row, int col, int mouseBtn);

}
