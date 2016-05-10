package es.ucm.fdi.tp.assignment4.ataxx;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.Utils;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.FiniteRectBoard;
import es.ucm.fdi.tp.basecode.bgame.model.Game.State;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Pair;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

/**
 * Rules for Ataxx game.
 * <ul>
 * <li>The game is played on an NxN board (with N>=5 and Nł2).</li>
 * </ul>
 * 
 * @author Müller András
 *
 */
public class AtaxxRules implements GameRules {

	protected final Pair<State, Piece> gameInPlayResult = new Pair<State, Piece>(
			State.InPlay, null);

	protected final Pair<State, Piece> gameDrawResult = new Pair<State, Piece>(
			State.Draw, null);

	private final int dim;
	private final int qObs;

	private Piece obstPiece;

	/**
	 * Initializes AtaxxRuless class with the dimension of the row and columns
	 * of Ataxx table.
	 * 
	 * @param qObstacles
	 *            must be at least 0 and not more than the square of the half of
	 *            {@code dim}.
	 * @param dim
	 *            must be at least 5 and an odd number
	 */
	public AtaxxRules(int qObstacles, int dim) {

		if (dim < 5) {
			throw new GameError("Dimension must be at least 5: " + dim);
		}

		if ((dim & 1) == 0) {
			throw new GameError("Dimension must be an odd number: " + dim);
		}

		if (qObstacles < 0) {
			throw new GameError("The number of obstacles cannot be negative: "
					+ qObstacles);
		}

		if (qObstacles >= Math.pow(dim / 2, 2)) {
			throw new GameError("The number of obstacles (" + qObstacles
					+ ") must less than " + (int) Math.pow(dim / 2, 2) + ".");
		}

		this.dim = dim;
		this.qObs = qObstacles;
	}

	@Override
	public String gameDesc() {
		return "Ataxx " + dim + "x" + dim;
	}

	@Override
	public Board createBoard(List<Piece> pieces) {
		FiniteRectBoard board = new FiniteRectBoard(dim, dim);

		board.setPosition(0, 0, pieces.get(0));
		board.setPosition(dim - 1, dim - 1, pieces.get(0));
		board.setPosition(0, dim - 1, pieces.get(1));
		board.setPosition(dim - 1, 0, pieces.get(1));

		if (pieces.size() >= 3) {
			board.setPosition(dim / 2, 0, pieces.get(2));
			board.setPosition(dim / 2, dim - 1, pieces.get(2));
		}

		if (pieces.size() == 4) {
			board.setPosition(0, dim / 2, pieces.get(3));
			board.setPosition(dim - 1, dim / 2, pieces.get(3));
		}

		if (qObs > 0) {
			obstPiece = getUniquePiece(pieces);
		}

		int q = qObs;
		while (q > 0) {
			ArrayList<Pair<Integer, Integer>> l = AtaxxCommon
					.emptyPlacesQuadrant(board);
			if (l.size() == 0) {
				q = 0;
			} else {
				Pair<Integer, Integer> p = l.get(Utils.randomInt(l.size()));
				AtaxxCommon.setInAllQuadrants(p.getFirst(), p.getSecond(),
						board, obstPiece);
				--q;
			}
		}

		return board;
	}

	@Override
	public Piece initialPlayer(Board board, List<Piece> pieces) {
		return nextPlayer(board, pieces, pieces.get(pieces.size() - 1));
	}

	/**
	 * Number of players can be 2, 3, or 4.
	 */
	@Override
	public int minPlayers() {
		return 2;
	}

	/**
	 * Number of players can be 2, 3, or 4.
	 */
	@Override
	public int maxPlayers() {
		return 4;
	}

	@Override
	public Pair<State, Piece> updateState(Board board, List<Piece> pieces,
			Piece turn) {
		boolean canContinue = false;
		canContinue = AtaxxCommon.gameCanContinue(board, pieces);

		if (!canContinue) {
			Piece winner = pieces.get(0);
			int highScore = AtaxxCommon.pieceOnBoardCount(board, winner);
			boolean draw = false;

			for (int i = 1; i < pieces.size(); ++i) {
				Piece currP = pieces.get(i);
				int currS = AtaxxCommon.pieceOnBoardCount(board, currP);

				if (highScore < currS) {
					winner = currP;
					highScore = currS;
					draw = false;
				} else if (highScore == currS) {
					draw = true;
				}
			}

			if (draw) {
				return gameDrawResult;
			} else {
				return new Pair<State, Piece>(State.Won, winner);
			}
		} else {
			Piece winner = AtaxxCommon.uniqueWinner(board, pieces);

			if (null != winner) {
				return new Pair<State, Piece>(State.Won, winner);
			}
		}

		return gameInPlayResult;
	}

	@Override
	public Piece nextPlayer(Board board, List<Piece> playersPieces, Piece turn) {
		List<Piece> pieces = playersPieces;
		int i = pieces.indexOf(turn);
		int c = pieces.size();
		Piece p = pieces.get((i + 1) % pieces.size());

		while (!AtaxxCommon.playerCanMove(board, p)) {
			p = pieces.get((++i + 1) % pieces.size());

			if (--c <= 0) {
				return null;
			}
		}

		return p;
	}

	@Override
	public List<GameMove> validMoves(Board board, List<Piece> playersPieces,
			Piece turn) {
		List<GameMove> moves = new ArrayList<GameMove>();
		for (int i = 0; i < board.getRows(); ++i) {
			for (int j = 0; j < board.getCols(); ++j) {
				if (turn.equals(board.getPosition(i, j))) {
					List<Pair<Integer, Integer>> empties = AtaxxCommon
							.emptyPlacesInRange(board, i, j);
					for (Pair<Integer, Integer> e : empties) {
						moves.add(new AtaxxMove(i, j, e.getFirst(), e
								.getSecond(), turn));
					}
				}
			}
		}
		return moves;
	}

	@Override
	public double evaluate(Board board, List<Piece> pieces, Piece turn, Piece p) {
		double n = AtaxxCommon.pieceOnBoardCount(board, turn);
		double m = 0;

		for (Piece e : pieces) {
			m += !e.equals(p) ? AtaxxCommon.pieceOnBoardCount(board, e) : 0;
		}

		double s = n + m;
		return (n / s) - (m / s);
	}

	private Piece getUniquePiece(List<Piece> pl) {
		int i = 0;

		while (true) {
			Piece p = new Piece("*#" + i++);

			if (!pl.contains(p)) {
				return p;
			}
		}
	}
}
