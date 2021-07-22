package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_FACE_PLAYER)
public interface PacketOutFacePlayer extends PacketPlay, PacketOutbound {
	
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
		return OUT_FACE_PLAYER;
	}

}
