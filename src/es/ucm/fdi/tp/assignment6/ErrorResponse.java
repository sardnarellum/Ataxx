package es.ucm.fdi.tp.assignment6;

import es.ucm.fdi.tp.basecode.bgame.model.GameObserver;

public class ErrorResponse implements Response {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;

	public ErrorResponse(String message) {
		this.message = message;
	}

	@Override
	public void run(GameObserver o) {
		o.onError(message);
	}

}
