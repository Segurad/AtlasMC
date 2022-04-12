package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_ENTITY_VELOCITY)
public interface PacketOutEntityVelocity extends PacketPlay, PacketOutbound {
	
	public int getEntityID();
	
	public double getVelocityX();
	
	public double getVelocityY();
	
	public double getVelocityZ();
	
	public void setVelocity(double x, double y, double z);
	
	@Override
	public default int getDefaultID() {
		return OUT_ENTITY_VELOCITY;
	}

}
