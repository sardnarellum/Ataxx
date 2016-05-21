package es.ucm.fdi.tp.assignment5;

import java.io.Serializable;

import es.ucm.fdi.tp.assignment5.Main.PlayerMode;

public final class PlayerModeExt implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
