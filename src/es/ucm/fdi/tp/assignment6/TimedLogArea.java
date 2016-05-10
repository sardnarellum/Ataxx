package es.ucm.fdi.tp.assignment6;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * {@link LogArea} extended with the ability of timestamping every log entries.
 * 
 * @author Müller András
 *
 */
@SuppressWarnings("serial")
public class TimedLogArea extends LogArea {
	final SimpleDateFormat sdf;
	final String s;
	
	/**
	 * Constructor with default parameters:
	 * <ul>
	 * <li>time format: "HH:mm:ss"</li>
	 * <li>s format: " - "</li>
	 * </ul>
	 */
	public TimedLogArea(){
		this("HH:mm:ss", " - ");
	}
	
	public TimedLogArea(String format, String separator){
		sdf = new SimpleDateFormat(format);
		s = separator;
	}
	
	@Override
	public void addLine(String t){
		final Calendar cal = Calendar.getInstance();
		super.addLine(sdf.format(cal.getTime()).toString() + s + t);
	}
}
