package es.ucm.fdi.tp.assignment6;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class TimeOutPlayer extends Player {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static ExecutorService service;

	private final Player player;
	private int to;

	public TimeOutPlayer(Player player, int timeOut) {
		this.player = player;
		this.setTimeOut(timeOut);
		service = Executors.newSingleThreadExecutor();
	}

	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		Future<GameMove> f = service.submit(new Callable<GameMove>() {
			@Override
			public GameMove call() throws Exception {
				return player.requestMove(p, board, pieces, rules);
			}
		});

		try {
			return f.get(to, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		} catch (TimeoutException e) {
			throw new GameError("The movement have not finished in " + to + " seconds.");
		} finally {
			f.cancel(true);
		}

		return null;
	}

	public void setTimeOut(int seconds) {
		this.to = seconds;
	}
}
