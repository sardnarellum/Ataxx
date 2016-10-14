package es.ucm.fdi.tp.assignment4.algorithm;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;

public interface GameAlgorithm {

	Piece winner(Board board, List<Piece> pieces);
}
