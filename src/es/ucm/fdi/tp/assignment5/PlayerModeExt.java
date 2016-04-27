package es.ucm.fdi.tp.assignment5;

import es.ucm.fdi.tp.assignment5.Main.PlayerMode;

public final class PlayerModeExt {
	
	private final PlayerMode pm;
	
	public PlayerModeExt(PlayerMode m){
		pm = m;
	}
	
	public PlayerMode getPlayerMode(){
		return pm;
	}
	
	@Override
	public String toString(){
		return pm.getDesc();
	}
}
