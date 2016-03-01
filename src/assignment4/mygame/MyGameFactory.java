package assignment4.mygame;

import es.ucm.fdi.tp.basecode.bgame.model.GameRules;
import es.ucm.fdi.tp.basecode.connectN.ConnectNFactory;

public class MyGameFactory extends ConnectNFactory {
	
	private int dim;
	
	public MyGameFactory(){
		dim = 4;
	}
	
	public MyGameFactory(int dim) {
		super(dim);
		this.dim = dim;
	}
	
	@Override
	public GameRules gameRules() {
		return new MyGameRule(dim);
	}
}
