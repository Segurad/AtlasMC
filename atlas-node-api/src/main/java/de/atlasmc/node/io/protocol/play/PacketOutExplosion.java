package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.sound.Sound;
import de.atlasmc.node.world.particle.Particle;

@DefaultPacketID(packetID = PacketPlay.OUT_EXPLOSION, definition = "explode")
public class PacketOutExplosion extends AbstractPacket implements PacketPlayOut {

	public double x;
	public double y;
	public double z;
	public float radius;
	public int blockCount;
	public double motionX = Float.NaN;
	public double motionY = Float.NaN;
	public double motionZ = Float.NaN;
	public Particle particle;
	public Sound sound;
	// TODO alternatives
	
	public boolean hasMotion() {
		return motionX == motionX && motionY == motionY && motionZ == motionZ;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_EXPLOSION;
	}
	
}
