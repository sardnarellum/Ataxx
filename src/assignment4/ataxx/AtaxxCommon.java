package assignment4.ataxx;

import es.ucm.fdi.tp.basecode.bgame.model.Board;

public final class AtaxxCommon {
	public static void RunInRange(int row, int col, int dist, Board board, Runnable r){
		int n = (0 + dist <= row ? row - dist : 0);
		int m = (row < board.getRows() - dist ? row + dist : row);
		int p = (0 + dist < col ? col - dist : 0);
		int q = (col < board.getCols() - dist ? col + dist : col);
		
		for (int i = n; i <= m; ++i){			
			for (int j = p; j <= q; ++j){
				//r.run(i, j);
			}
		}
	}
}
