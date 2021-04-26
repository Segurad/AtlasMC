package de.atlasmc.entity;

import java.util.UUID;

import de.atlasmc.Location;
import de.atlasmc.SimpleLocation;
import de.atlasmc.Vector;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.world.World;

public interface Entity extends NBTHolder {

	public enum Animation {
		SWING_MAIN_ARM,
		TAKE_DAMAGE,
		LEAVE_BED,
		SWING_OFFHAND,
		CRITICAL_EFFECT,
		MAGIC_CRITICAL_EFFECT;
		
		public static Animation getByID(int id) {
			return values()[id];
		}
	}
	public enum Pose {
		STANDING,
		FALL_FLYING,
		SLEEPING,
		SWIMMING,
		SPIN_ATTACK,
		SNEAKING,
		DYING;
		
		public static Pose getByID(int id) {
			return values()[id];
		}
		
		public int getID() {
			return ordinal();
		}
	}
	public int getAirTicks();
	
	public String getCustomName();
	public float getFallDistance();
	public short getFireTicks();
	/**
	 * 
	 * @return the internal entity id
	 */
	public int getID();
	
	public Location getLocation();
	public Location getLocation(Location loc);
	
	public SimpleLocation getLocation(SimpleLocation loc);
	public int getObjectData();
	public Pose getPose();
	
	public LocalServer getServer();
	public EntityType getType();
	public UUID getUUID();
	public Vector getVelocity();
	
	public Vector getVelocity(Vector vec);
	public World getWorld();
	
	public double getX();
	public double getY();
	public double getZ();
	public boolean hasGravity();
	public boolean hasVelocity();
	public boolean isCrouching();

	public boolean isCustomNameVisible();
	
	public boolean isFlyingWithElytra();
	public boolean isGlowing();
	public boolean isInvisble();
	public boolean isOnFire();
	public boolean isSilent();
	public boolean isSprinting();
	public boolean isSwimming();
	public void remove();
	public void setCustomName(String name);
	
	public void setCustomNameVisible(boolean value);
	public void setFallDistance(float distance);

	public void setFireTicks(int ticks);
	
	public void setGlowing(boolean glowing);
	
}
