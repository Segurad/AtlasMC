package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_EXPLOSION)
public interface PacketOutExplosion extends PacketPlay, PacketOutbound {

	public float getX();
	public float getY();
	public float getZ();
	public float getStrength();
	public byte[] getRecords();
	public float getMotionX();
	public float getMotionY();
	public float getMotionZ();
	
	@Override
	default int getDefaultID() {
		return OUT_EXPLOSION;
	}
	
}
