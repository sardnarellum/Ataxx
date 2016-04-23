package es.ucm.fdi.tp.assignment5;

import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class PlayerTableModel extends DefaultTableModel {

	private final String colNames[];

	public PlayerTableModel() {
		colNames = new String[] { "Player", "Mode", "Pieces" };
	}
	
	public void refresh(){
		fireTableDataChanged();
	}
	
	@Override
	public String getColumnName(int col){
		return colNames[col];
	}

	@Override
	public int getRowCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getColumnCount() {
		return colNames.length;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		// TODO Auto-generated method stub
		return null;
	}

}
