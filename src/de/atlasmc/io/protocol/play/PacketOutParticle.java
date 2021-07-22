package de.atlasmc.io.protocol.play;

import de.atlasmc.Particle;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_PARTICLE)
public interface PacketOutParticle extends PacketPlay, PacketOutbound {
	
	public Particle getParticle();
	public boolean isLongDistance();
	public double getX();
	public double getY();
	public double getZ();
	public float getOffsetX();
	public float getOffsetY();
	public float getOffsetZ();
	public float getParticleData();
	public int getParticleCount();
	public byte[] getData();
	
	@Override
	public default int getDefaultID() {
		return OUT_PARTICLE;
	}

}
