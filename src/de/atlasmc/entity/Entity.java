package de.atlasmc.entity;

import java.util.UUID;

import de.atlasmc.Location;
import de.atlasmc.SimpleLocation;
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
	public Location getLocation(Location loc);
	public SimpleLocation getLocation(SimpleLocation loc);
	public World getWorld();
	public LocalServer getServer();
	
	public enum Pose {
		DYING,
		FALL_FLYING,
		SLEEPING,
		SPIN_ATTACK,
		STANDING,
		SWIMMING
	}
	
	public enum Animation {
		SWING_MAIN_ARM,
		TAKE_DAMAGE,
		LEAVE_BED,
		SWING_OFFHAND,
		CRITICAL_EFFECT,
		MAGIC_CRITICAL_EFFECT;
		
		public static Animation getByID(int id) {
			Animation[] values = values();
			return values[id];
		}
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
	public Vector getVelocity(Vector vec);
	public boolean hasVelocity();
	public double getX();
	public double getY();
	public double getZ();
	
}
