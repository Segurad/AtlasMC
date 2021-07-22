package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_ENTITY_POSITION)
public interface PacketOutEntityPosition extends PacketPlay, PacketOutbound {

	public int getEntityID();
	public short getDeltaX();
	public short getDeltaY();
	public short getDeltaZ();
	public boolean isOnGround();
	
	@Override
	default int getDefaultID() {
		return OUT_ENTITY_POSITION;
	}
	
}
