package es.ucm.fdi.tp.assignment5;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import es.ucm.fdi.tp.assignment5.Main.PlayerMode;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class PlayerTableModel extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String colNames[];
	private List<Piece> pieces;
	private Map<Piece, PlayerMode> modes;
	private Map<Piece, Integer> scores;
	private Boolean showScores;

	public PlayerTableModel() {
		colNames = new String[] { "Player", "Mode", "Pieces" };
		pieces = new ArrayList<Piece>();
		modes = new HashMap<Piece, PlayerMode>();
		scores = new HashMap<Piece, Integer>();
		showScores = true;
	}

	public void refresh() {
		fireTableDataChanged();
	}
	
	public void setRow(Piece p, PlayerMode m){
		addPieceIfNotExists(p);
		modes.put(p, m);
	}
	
	public void setRow(Piece p, PlayerMode m, Integer s){
		setRow(p, m);
		scores.put(p, s);
	}

	public void setMode(Piece p, PlayerMode m) {
		addPieceIfNotExists(p);
		modes.put(p, m);
	}

	public void setScore(Piece p, Integer s) {
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
			return modes.get(pieces.get(rowIndex)).getDesc();
		}
		else if (2 == columnIndex){
			return scores.get(pieces.get(rowIndex));
		}
		
		return null;
	}

}
