package de.atlasmc.io.protocol.play;

import de.atlasmc.Particle;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.sound.Sound;

@DefaultPacketID(packetID = PacketPlay.OUT_EXPLOSION, definition = "explode")
public class PacketOutExplosion extends AbstractPacket implements PacketPlayOut {

	public float x;
	public float y;
	public float z;
	public float strength;
	public byte[] records;
	public float motionX;
	public float motionY;
	public float motionZ;
	public int blockInteraction;
	public Particle smallExplosionParticle;
	public Object smallExplosionParticleData;
	public Particle largeExplosionParticle;
	public Object largeExplosionParticleData;
	public Sound explosionSound;
	
	@Override
	public int getDefaultID() {
		return OUT_EXPLOSION;
	}
	
}
