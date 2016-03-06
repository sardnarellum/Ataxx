package assignment4.ataxx;

import es.ucm.fdi.tp.basecode.bgame.model.GameRules;

public class AtaxxObstaclesFactory extends AtaxxFactory {
	private int qObs;

	public AtaxxObstaclesFactory(int qObs) {
		super();
		this.qObs = qObs;
	}

	public AtaxxObstaclesFactory(int qObs, int dim) {
		super(dim);
		this.qObs = qObs;
	}
	
	@Override
	public GameRules gameRules(){
		return new AtaxxObstaclesRules(qObs, super.dim);
	}

}
