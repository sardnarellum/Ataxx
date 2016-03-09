package assignment4.ataxx;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.Utils;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class AtaxxRandomPlayer extends Player {

	private static final long serialVersionUID = 1L;
	
	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		if (board.isFull()) {
			throw new GameError("The board is full, cannot make a random move!!");
		}

		int rows = board.getRows();
		int cols = board.getCols();

		// pick an initial random position
		int currRow = Utils.randomInt(rows);
		int currCol = Utils.randomInt(cols);

		// start at (currRow,currColl) and look for the first position matching with the piece for origin.
		while (true) {
			if (board.getPosition(currRow, currCol) == p &&
				AtaxxCommon.boardEmptyInRange(board, currRow, currCol)) {
				
				// pick an inital random position for the destiniation 
				int shiftR = Utils.randomInt(4) - 2;
				int shiftC = Utils.randomInt(4) - 2;
				while (true) {					
					if (currRow + shiftR >= rows || currRow + shiftR < 0){
						shiftR *= -1;
					}
					
					if (currCol + shiftC >= cols || currCol + shiftC < 0){
						shiftC *= -1;
					}
					
					if (board.getPosition(currRow + shiftR, currCol + shiftC) == null){
						return createMove(currRow, currCol,
								currRow + shiftR, currCol + shiftC, p);
					}
					
					shiftR = Utils.randomInt(4) - 2;
					shiftC = Utils.randomInt(4) - 2;
				}
			}
			currCol = (currCol + 1) % cols;
			if (currCol == 0) {
				currRow = (currRow + 1) % rows;
			}
		}
	}

	private GameMove createMove(int currRowF, int currColF,
			int currRowT, int currColT, Piece p) {
		return new AtaxxMove(currRowF, currColF, currRowT, currColT, p); // dummy arguments
	}

}
