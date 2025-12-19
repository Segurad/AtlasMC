package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.world.particle.Particle;

@DefaultPacketID(packetID = PacketPlay.OUT_PARTICLE, definition = "level_particles")
public class PacketOutParticle extends AbstractPacket implements PacketPlayOut {
	
	public boolean longDistance;
	public double x;
	public double y; 
	public double z;
	public float offX;
	public float offY;
	public float offZ;
	public float maxSpeed;
	public int count;
	public Particle particle;

	@Override
	public int getDefaultID() {
		return OUT_PARTICLE;
	}

}
