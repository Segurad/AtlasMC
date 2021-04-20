package de.atlasmc.entity;

import de.atlasmc.Vector;
import de.atlasmc.io.protocol.play.PacketOutExplosion;

public interface Fireball extends Projectile, PacketOutExplosion {
	
	public Vector getDirection();
	public void setDirection(Vector direction);

}
