package de.atlasmc.entity;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.joml.Vector3d;

import de.atlasmc.Location;
import de.atlasmc.Nameable;
import de.atlasmc.SimpleLocation;
import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import de.atlasmc.server.LocalServer;
import de.atlasmc.tick.Tickable;
import de.atlasmc.util.ViewerSet;
import de.atlasmc.util.annotation.ThreadSafe;
import de.atlasmc.util.nbt.CustomTagContainer;
import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.EntityTracker.Perception;
import de.atlasmc.world.World;

public interface Entity extends NBTHolder, Nameable, Tickable {

	public enum Animation {
		SWING_MAIN_ARM,
		TAKE_DAMAGE,
		LEAVE_BED,
		SWING_OFFHAND,
		CRITICAL_EFFECT,
		MAGIC_CRITICAL_EFFECT;
		
		private static List<Animation> VALUES;
		
		public static Animation getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Animation> getValues() {
			if (VALUES == null)
				synchronized (Animation.class) {
					if (VALUES == null)
						VALUES = List.of(values());
				}
			return VALUES;
		}
		
		public int getID() {
			return ordinal();
		}
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
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
		
		private static List<Pose> VALUES;
		
		public static Pose getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Pose> getValues() {
			if (VALUES == null)
				synchronized (Pose.class) {
					if (VALUES == null)
						VALUES = List.of(values());
				}
			return VALUES;
		}
		
		public int getID() {
			return ordinal();
		}
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}
		
	}
	void addScoreboardTag(String tag);
	
	int getAirTicks();
	
	float getFallDistance();
	
	short getFireTicks();
	
	/**
	 * 
	 * @return the internal entity id
	 */
	int getID();
	
	Location getLocation();
	
	Location getLocation(Location loc);
	
	SimpleLocation getLocation(SimpleLocation loc);
	
	CustomTagContainer getCustomTagContainer();
	
	Pose getPose();
	
	Collection<String> getScoreboardTags();
	
	LocalServer getServer();
	
	EntityType getType();
	
	/**
	 * Returns the UUID of this Entity
	 * @return UUID
	 */
	UUID getUUID();
	
	Vector3d getVelocity();
	
	Vector3d getVelocity(Vector3d vec);
	
	void setVelocity(Vector3d vec);
	
	void setVelocity(double x, double y, double z);
	
	World getWorld();
	
	Chunk getChunk();
	
	double getX();
	
	double getY();
	
	double getZ();
	
	float getPitch();

	float getYaw();
	
	boolean hasGravity();
	
	boolean hasScoreboardTags();
	
	boolean hasVelocity();

	boolean isCustomNameVisible();
	
	boolean isFlyingWithElytra();
	
	boolean isGlowing();
	
	boolean isInvisible();
	
	void setInvisible(boolean invisible);
	
	boolean isOnGround();
	
	boolean isOnFire();
	
	boolean isSilent();
	
	boolean isSprinting();
	
	boolean isSwimming();
	
	boolean hasCustomName();
	
	boolean isInvulnerable();
	
	/**
	 * Removes the entity the current world
	 */
	void remove();
	
	/**
	 * Thread safe alternative to {@link #isRemoved()}.<br>
	 * Useage e.g. for other servers or worlds preparing the spawning of this entity.
	 * @return true if the Entity is removed
	 */
	@ThreadSafe
	boolean asyncIsRemoved();
	
	/**
	 * Check if the Entity is removed
	 * @return true if the Entity is removed
	 */
	boolean isRemoved();
	
	boolean isDead();
	
	int getPortalCooldown();
	
	void setAirTicks(int air);

	void setCustomNameVisible(boolean value);

	void setFallDistance(float distance);
	
	void setFireTicks(int ticks);

	void setGlowing(boolean glowing);

	void setGravity(boolean gravity);
	
	void setInvulnerable(boolean invulnerable);

	void setPortalCooldown(int cooldown);

	void setPose(Pose pose);

	void setSilent(boolean silent);

	void setUUID(UUID uuid);
	
	int getFreezeTicks();
	
	void setFreezeTicks(int ticks);
	
	public default void teleport(SimpleLocation loc) {
		if (loc == null)
			throw new IllegalArgumentException("Location can not be null!");
		teleport(loc.x, loc.y, loc.z, loc.yaw, loc.pitch);
	}
	
	/**
	 * Teleport to new coordinates and keeps yaw and pitch
	 * @param x
	 * @param y
	 * @param z
	 */
	void teleport(double x, double y, double z);
	
	void teleport(double x, double y, double z, float yaw, float pitch);

	/**
	 * This method is called by the System when the entity is spawned at another world and/or server.<br>
	 * It resets the removed status when used.
	 * @param world the new world of this entity
	 * @param x
	 * @param y
	 * @param z
	 * @param pitch
	 * @param yaw
	 */
	void spawn(World world, double x, double y, double z, float pitch, float yaw);
	
	void causeSound(Sound sound, SoundCategory category, float volume, float pitch, long seed);
	
	void causeSound(String sound, SoundCategory category, float volume, float pitch, long seed, boolean fixedRange, float range);
	
	ViewerSet<Entity, Player> getViewers();
	
	Perception<?> getPerception();
	
	void setPerception(Perception<?> perception);
	
	double getPerceptionDistance();
	
	void setPerceptionDistance(double distance);
	
}
