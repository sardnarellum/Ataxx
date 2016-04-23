package es.ucm.fdi.tp.assignment5;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import es.ucm.fdi.tp.assignment5.Main.PlayerMode;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

@SuppressWarnings("serial")
public class PlayerTableModel extends AbstractTableModel {

	private final String colNames[];
	private List<Piece> pieces;
	private Map<Piece, Color> colors;
	private Map<Piece, PlayerMode> modes;
	private Map<Piece, Integer> scores;
	private Boolean showScores;

	public PlayerTableModel() {
		colNames = new String[] { "Player", "Mode", "Pieces" };
		pieces = new ArrayList<Piece>();
		colors = new HashMap<Piece, Color>();
		modes = new HashMap<Piece, PlayerMode>();
		scores = new HashMap<Piece, Integer>();
		showScores = true;
	}

	public void refresh() {
		fireTableDataChanged();
	}
	
	public void setRow(Piece p, Color c, PlayerMode m){
		addPieceIfNotExists(p);
		colors.put(p, c);
		modes.put(p, m);
	}
	
	public void setRow(Piece p, Color c, PlayerMode m, Integer s){
		setRow(p, c, m);
		scores.put(p, s);
	}

	public void setColor(Piece p, Color c) {
		addPieceIfNotExists(p);
		colors.put(p, c);
	}

	public void setMode(Piece p, PlayerMode m) {
		addPieceIfNotExists(p);
		modes.put(p, m);
	}

	public void setScore(Piece p, int s) {
		addPieceIfNotExists(p);
		scores.put(p, s);
	}
	
	private void addPieceIfNotExists(Piece p){
		if (!pieces.contains(p)){
			pieces.add(p);
		}
	}

	@Override
	public String getColumnName(int col) {
		return colNames[col];
	}

	@Override
	public int getRowCount() {
		return pieces.size();
	}

	@Override
	public int getColumnCount() {
		return showScores ? colNames.length : colNames.length - 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (0 == columnIndex){
			return pieces.get(rowIndex);
		}
		else if (1 == columnIndex){
			return modes.get(pieces.get(rowIndex));
		}
		else if (2 == columnIndex){
			return scores.get(pieces.get(rowIndex));
		}
		
		return null;
	}

}
