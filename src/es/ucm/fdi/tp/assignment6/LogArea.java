package es.ucm.fdi.tp.assignment6;

import javax.swing.JTextArea;

/**
 * A {@link JTextArea} with the ability to add messages as lines.
 * 
 * @author Müller András
 *
 */
@SuppressWarnings("serial")
public class LogArea extends JTextArea {
	
	/**
	 * Itt adds a new line to the {@link JTextArea} superclass.
	 * 
	 * @param t The {@link String} which will be added as a new line.
	 */
	public void addLine(String t) {
		if (0 == getText().length()) {
			setText(t);
		} else {
			append("\n" + t);
		}
	}
}
