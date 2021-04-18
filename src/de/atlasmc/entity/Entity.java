package de.atlasmc.entity;

import java.util.UUID;

import de.atlasmc.Location;
import de.atlasmc.Vector;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.world.World;

public interface Entity extends NBTHolder {

	public boolean isOnFire();
	public boolean isCrouching();
	public boolean isSprinting();
	public boolean isSwimming();
	public boolean isInvisble();
	public boolean isGlowing();
	public boolean isFlyingWithElytra();
	public int getAirTicks();
	public String getCustomName();
	public boolean isSilent();
	public boolean isCustomNameVisible();
	public boolean hasGravity();
	public Pose getPose();
	
	public void remove();
	public void setCustomNameVisible(boolean value);
	public void setCustomName(String name);
	public Location getLocation();
	public World getWorld();
	public LocalServer getServer();
	
	public enum Pose {
		DYING,
		FALL_FLYING,
		SLEEPING,
		SPIN_ATTACK,
		STANDING,
		SWIMMING,
	}

	public EntityType getType();
	
	/**
	 * 
	 * @return the internal entity id
	 */
	public int getID();
	public UUID getUUID();
	public int getObjectData();
	public Vector getVelocity();
	public boolean hasVelocity();
	public double getX();
	public double getY();
	public double getZ();
	
}