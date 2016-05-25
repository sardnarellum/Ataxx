package es.ucm.fdi.tp.basecode.bgame.control.commands;

import es.ucm.fdi.tp.basecode.bgame.control.Controller;

/**
 * A 'QUIT' command. It executes {@link Controller#stopGame()} of the corresponding
 * controller.
 *
 * <p>
 * Comando 'QUIT'. Ejecuta el metodo {@link Controller#stopGame()} del controlador
 * correspondiente.
 */
public class QuitCommand implements Command {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void execute(Controller c) {
		c.stopGame();
	}

	@Override
	public String helpText() {
		return "QUIT: exit the game.";
	}

	@Override
	public Command parse(String cmd) {
		if (cmd.trim().equalsIgnoreCase("quit")) {
			return this;
		} else {
			return null;
		}
	}

}
