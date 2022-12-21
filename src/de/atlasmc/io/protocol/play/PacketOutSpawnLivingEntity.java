package de.atlasmc.io.protocol.play;

import java.util.UUID;

import de.atlasmc.Vector;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.LivingEntity;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SPAWN_LIVING_ENTITY)
public class PacketOutSpawnLivingEntity extends AbstractPacket implements PacketPlayOut {
	
	private int entityID;
	private EntityType type;
	private float yaw, pitch, headpitch;
	private UUID uuid;
	private double x, y, z;
	private double velocityX, velocityY, velocityZ;
	
	public int getEntityID() {
		return entityID;
	}

	public EntityType getType() {
		return type;
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public float getHeadpitch() {
		return headpitch;
	}

	public UUID getUUID() {
		return uuid;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public double getVelocityX() {
		return velocityX;
	}

	public double getVelocityY() {
		return velocityY;
	}

	public double getVelocityZ() {
		return velocityZ;
	}

	public void setEntityID(int entityID) {
		this.entityID = entityID;
	}

	public void setType(EntityType type) {
		this.type = type;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setHeadpitch(float headpitch) {
		this.headpitch = headpitch;
	}

	public void setUUID(UUID uuid) {
		this.uuid = uuid;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setVelocityX(double velocityX) {
		this.velocityX = velocityX;
	}

	public void setVelocityY(double velocityY) {
		this.velocityY = velocityY;
	}

	public void setVelocityZ(double velocityZ) {
		this.velocityZ = velocityZ;
	}

	public void setEntity(LivingEntity entity) {
		entityID = entity.getID();
		uuid = entity.getUUID();
		type = entity.getType();
		x = entity.getX();
		y = entity.getY();
		z = entity.getZ();
		yaw = entity.getYaw();
		pitch = entity.getPitch();
		headpitch = entity.getHeadPitch();
		if (entity.hasVelocity()) {
			Vector v = entity.getVelocity();
			velocityX = v.getX();
			velocityY = v.getY();
			velocityZ = v.getZ();
		}
	}
	
	@Override
	public int getDefaultID() {
		return OUT_SPAWN_LIVING_ENTITY;
	}

}
