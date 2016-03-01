package assignment4.mygame;

import java.util.List;

import es.ucm.fdi.tp.basecode.bgame.model.Board;
import es.ucm.fdi.tp.basecode.bgame.model.Game;
import es.ucm.fdi.tp.basecode.bgame.model.Pair;
import es.ucm.fdi.tp.basecode.bgame.model.Piece;
import es.ucm.fdi.tp.basecode.connectN.ConnectNRules;

public class MyGameRule extends ConnectNRules {

	public MyGameRule(int dim){ 
		super(dim);
	}
	
	@Override
	public String gameDesc() {
		return "My Game";
	}
	
	@Override
	public Pair<Game.State, Piece> updateState(Board board, List<Piece> playerPieces, Piece lastPlayer){
		return null;
	}
	
}
