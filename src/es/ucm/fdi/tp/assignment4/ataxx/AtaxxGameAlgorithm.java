package es.ucm.fdi.tp.assignment4.ataxx;

import java.util.List;

import es.ucm.fdi.tp.assignment4.algorithm.BasicBoardAlgorithm;
import es.ucm.fdi.tp.assignment4.algorithm.BoardAlgorithm;
import es.ucm.fdi.tp.assignment4.algorithm.GameAlgorithm;
import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class AtaxxGameAlgorithm implements GameAlgorithm {

	@Override
	public Piece winner(Board board, List<Piece> pieces) {
		BoardAlgorithm bAlgo = new BasicBoardAlgorithm();
		Piece winner = pieces.get(0);
		int highScore = bAlgo.pieceOnBoardCount(board, winner);

		for (int i = 1; i < pieces.size(); ++i) {
			Piece currP = pieces.get(i);
			int currS = bAlgo.pieceOnBoardCount(board, currP);

			if (highScore < currS) {
				winner = currP;
				highScore = currS;
			} else if (highScore == currS) {
				winner = null;
			}
		}
		
		return winner;
	}

}
