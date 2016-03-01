package assignment.ataxx;

import java.util.ArrayList;
import java.util.List;

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
 * @author Müller András
 *
 */
public class AtaxxRules implements GameRules {
	
	protected final Pair<State, Piece> gameInPlayResult =
			new Pair<State, Piece>(State.InPlay, null);
	
	private int dim;
	
	/**
	 * Initializes AtaxxRuless class with the dimension of the row and columns of Ataxx table.
	 * @param dim must be at least 5 and an odd number
	 */
	public AtaxxRules(int dim){
		if (dim < 5){
			throw new GameError("Dimension must be at least 5: " + dim);
		}
		
		if ((dim & 1) == 0){
			throw new GameError("Dimension must be an odd number: " + dim);
		}
		
		this.dim = dim;
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
		
		if (pieces.size() >= 3){
			board.setPosition(dim / 2, 0, pieces.get(2));
			board.setPosition(dim / 2, dim - 1, pieces.get(2));
		}
		
		if (pieces.size() == 4){
			board.setPosition(0, dim / 2, pieces.get(3));
			board.setPosition(dim - 1, dim / 2, pieces.get(3));			
		}
		
		return board;
	}

	@Override
	public Piece initialPlayer(Board board, List<Piece> pieces) {
		return pieces.get(0);
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
	public Pair<State, Piece> updateState(Board board, List<Piece> pieces, Piece turn) {
		// TODO implement state updating
		return gameInPlayResult;
	}

	@Override
	public Piece nextPlayer(Board board, List<Piece> playersPieces, Piece turn) {
		List<Piece> pieces = playersPieces;
		int i = pieces.indexOf(turn);
		return pieces.get((i + 1) % pieces.size());
	}

	@Override
	public double evaluate(Board board, List<Piece> pieces, Piece turn) {
		return 0;
	}

	@Override
	public List<GameMove> validMoves(Board board, List<Piece> playersPieces, Piece turn) {
		List <GameMove> moves = new ArrayList<GameMove>();		
		
		for (int row = 0; row < board.getRows(); ++row){
			for (int col = 0; col < board.getCols(); ++col){
				if (board.getPosition(row, col) == null && inRange(row, col, board, playersPieces)){
					moves.add(new AtaxxMove(row, col, turn));
				}
			}
		}
		
		return moves;
	}
	
	private boolean inRange(int row, int col, Board board, List<Piece> playersPieces){
		int n = row < board.getRows() - 2 ? row + 2 : board.getRows() - 1;
		int m = row < board.getCols() - 2 ? col + 2 : board.getCols() - 1;
		
		for (int i = 1 < row ? row - 2 : 0; i <= n; ++i){			
			for (int j = 1 < col ? col - 2 : 0; i <= m; ++j){
				if (j < 0){
					j = 0;
				}
				if (i == j){
					++j;
				}
			}
		}
		
		return false;
	}

}
