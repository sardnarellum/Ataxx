package es.ucm.fdi.tp.assignment4.algorithm;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public class BasicBoardAlgorithm implements BoardAlgorithm {

	@Override
	public boolean pieceCanMove(Board board, Piece piece) {
		return anyEmptyPlace(board);
	}
	
	private boolean anyEmptyPlace(Board board){
		for (int i = 0; i < board.getRows(); ++i){
			for (int j = 0; j < board.getCols(); ++j){
				if (null == board.getPosition(i, j)){
					return true;
				}
			}
		}
		
		return false;
	}
}
