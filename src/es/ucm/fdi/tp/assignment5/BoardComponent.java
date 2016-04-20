package es.ucm.fdi.tp.assignment5;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public class BoardComponent extends JComponent {

	private int _CELL_HEIGHT = 50;
	private int _CELL_WIDTH = 50;

	private int rows;
	private int cols;
	private Color[][] board;

	public BoardComponent(int rows, int cols) {
		initBoard(rows, cols);
		initGUI();
	}

	private void initBoard(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		board = new Color[rows][cols];
		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				if (Math.random() > 0.5)
					board[i][j] = Color.BLUE;
				else
					board[i][j] = Color.RED;
	}

	private void initGUI() {
		this.setSize(new Dimension(rows * _CELL_HEIGHT, cols * _CELL_WIDTH));
		repaint();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		_CELL_WIDTH = this.getWidth() / cols;
		_CELL_HEIGHT = this.getHeight() / rows;

		for (int i = 0; i < rows; i++)
			for (int j = 0; j < cols; j++)
				drawCell(i, j, g);
	}

	private void drawCell(int row, int col, Graphics g) {
		int x = col * _CELL_WIDTH;
		int y = row * _CELL_HEIGHT;

		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x + 2, y + 2, _CELL_WIDTH - 4, _CELL_HEIGHT - 4);

		g.setColor(board[row][col]);
		g.fillOval(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);

		g.setColor(Color.black);
		g.drawOval(x + 4, y + 4, _CELL_WIDTH - 8, _CELL_HEIGHT - 8);

	}

	public void setBoardSize(int rows, int cols) {
		initBoard(rows, cols);
		repaint();
	}

}
