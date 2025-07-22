package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.sound.Sound;
import de.atlasmc.world.particle.ParticleType;

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
	public ParticleType smallExplosionParticle;
	public Object smallExplosionParticleData;
	public ParticleType largeExplosionParticle;
	public Object largeExplosionParticleData;
	public Sound explosionSound;
	
	@Override
	public int getDefaultID() {
		return OUT_EXPLOSION;
	}
	
}
