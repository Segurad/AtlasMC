package de.atlasmc.io.protocol.play;

import java.util.UUID;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_SPAWN_PLAYER)
public interface PacketOutSpawnPlayer extends PacketPlay, PacketOutbound {
	
	public int getEntityID();
	public UUID getUUID();
	public double getX();
	public double getY();
	public double getZ();
	public float getYaw();
	public float getPitch();
	
	@Override
	public default int getDefaultID() {
		return OUT_SPAWN_PLAYER;
	}

}
