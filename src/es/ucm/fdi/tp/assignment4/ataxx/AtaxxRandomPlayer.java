package es.ucm.fdi.tp.assignment4.ataxx;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.Utils;
import es.ucm.fdi.tp.basecode.bgame.control.Player;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.GameError;
import es.ucm.fdi.tp.basecode.bgame.model.GameMove;
import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.bgame.model.Pair;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class AtaxxRandomPlayer extends Player {

	private static final long serialVersionUID = 1L;
	
	@Override
	public GameMove requestMove(Piece p, Board board, List<Piece> pieces, GameRules rules) {
		if (!AtaxxCommon.playerCanMove(board, p)) {
			throw new GameError(p.getId() + "cannot make a random move!!");
		}

		int rows = board.getRows();
		int cols = board.getCols();

		// pick an initial random position
		int currRow = Utils.randomInt(rows);
		int currCol = Utils.randomInt(cols);

		// start at (currRow,currColl) and look for the first position matching with the piece for origin.
		while (true) {
			if (board.getPosition(currRow, currCol) == p) {
				List<Pair<Integer, Integer>> l = AtaxxCommon
						.emptyPlacesInRange(board, currRow, currCol);
				
				int emptyPlaces = l.size(); 
				
				if (emptyPlaces > 0){					
					int index = emptyPlaces > 1 ? Utils.randomInt(emptyPlaces - 1) : 0;
					Pair<Integer, Integer> coords = l.get(index);
					return createMove(currRow, currCol,	coords.getFirst(),
							coords.getSecond(), p);
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
