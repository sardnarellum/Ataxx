package assignment4.ataxx;

import es.ucm.fdi.tp.basecode.bgame.model.GameError;

public class AtaxxObstaclesRules extends AtaxxRules {
	
	private int qObstacles;

	public AtaxxObstaclesRules(int qObs, int dim) {
		super(dim);
		
		if (qObs < 0){
			throw new GameError("The number of obstacles cannot be negative: " + qObs);
		}
		
		this.qObstacles = qObs;
	}
}
