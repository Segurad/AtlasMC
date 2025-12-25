package de.atlasmc.node.io.protocol.play;

import org.joml.Vector3d;
import org.joml.Vector3f;

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
	
	public PacketOutParticle(Particle particle, Vector3d loc, Vector3f off, float maxSpeed, int count) {
		this.particle = particle;
		this.x = loc.x;
		this.y = loc.y;
		this.z = loc.z;
		if (off != null) {
			this.offX = off.x;
			this.offY = off.y;
			this.offZ = off.z;
		}
		this.maxSpeed = maxSpeed;
		this.count = count;
	}
	
	public PacketOutParticle() {
		// simple constructor
	}

	@Override
	public int getDefaultID() {
		return OUT_PARTICLE;
	}

}
