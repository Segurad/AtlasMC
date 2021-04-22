package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutFacePlayer extends Packet {
	
	/**
	 * 
	 * @return whether or not player should aim with his eyes or feet
	 */
	public boolean getAimWithEyes();
	public double getTargetX();
	public double getTargetY();
	public double getTargetZ();
	/**
	 * 
	 * @return whether or not the the player is facing towards an entity
	 */
	public boolean isEntity();
	public int getEntityID();
	/**
	 * 
	 * @return same as getAimWithEyes but with the entity
	 */
	public boolean getAimAtEyes();
	
	@Override
	default int getDefaultID() {
		return 0x33;
	}

}
