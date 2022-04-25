package de.atlasmc.entity;

import java.util.List;
import java.util.UUID;

import de.atlasmc.Location;
import de.atlasmc.Nameable;
import de.atlasmc.SimpleLocation;
import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import de.atlasmc.Vector;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.tick.Tickable;
import de.atlasmc.util.ViewerSet;
import de.atlasmc.util.annotation.ThreadSafe;
import de.atlasmc.util.nbt.CustomTagContainer;
import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.world.Chunk;
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
	public void addScoreboardTag(String tag);
	
	public int getAirTicks();
	
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
	
	public CustomTagContainer getCustomTagContainer();
	
	public Pose getPose();
	
	public List<String> getScoreboardTags();
	
	public LocalServer getServer();
	
	public EntityType getType();
	
	public UUID getUUID();
	
	public Vector getVelocity();
	
	public Vector getVelocity(Vector vec);
	
	public World getWorld();
	
	public Chunk getChunk();
	
	public double getX();
	
	public double getY();
	
	public double getZ();
	
	public float getPitch();

	public float getYaw();
	
	public boolean hasGravity();
	
	public boolean hasScoreboardTags();
	
	public boolean hasVelocity();

	public boolean isCustomNameVisible();
	
	public boolean isFlyingWithElytra();
	
	public boolean isGlowing();
	
	public boolean isInvisible();
	
	public void setInvisible(boolean invisible);
	
	public boolean isOnGround();
	
	public boolean isOnFire();
	
	public boolean isSilent();
	
	public boolean isSprinting();
	
	public boolean isSwimming();
	
	public boolean hasCustomName();
	
	public boolean isInvulnerable();
	
	/**
	 * Removes the entity the current world
	 */
	public void remove();
	
	/**
	 * Thread safe alternative to {@link #isRemoved()}.<br>
	 * Useage e.g. for other servers or worlds preparing the spawning of this entity.
	 * @return true if the Entity is removed
	 */
	@ThreadSafe
	public boolean asyncIsRemoved();
	
	/**
	 * Check if the Entity is removed
	 * @return true if the Entity is removed
	 */
	public boolean isRemoved();
	
	public boolean isDead();
	
	public int getPortalCooldown();
	
	public void setAirTicks(int air);

	public void setCustomNameVisible(boolean value);

	public void setFallDistance(float distance);
	
	public void setFireTicks(int ticks);

	public void setGlowing(boolean glowing);

	public void setGravity(boolean gravity);
	
	public void setInvulnerable(boolean invulnerable);

	public void setPortalCooldown(int cooldown);

	public void setPose(Pose pose);

	public void setSilent(boolean silent);

	public void setUUID(UUID uuid);

	public void setVelocity(double x, double y, double z);
	
	/**
	 * Teleport to new coordinates and keeps yaw and pitch
	 * @param x
	 * @param y
	 * @param z
	 */
	public void teleport(double x, double y, double z);
	
	public void teleport(double x, double y, double z, float yaw, float pitch);

	public void setOnGround(boolean onGround);

	/**
	 * This method is called by the System when the entity is spawned at another world and/or server.<br>
	 * It resets the removed status when used.
	 * @param entityID the new entityID of this entity
	 * @param world the new world of this entity
	 * @param x
	 * @param y
	 * @param z
	 * @param pitch
	 * @param yaw
	 */
	public void spawn(int entityID, World world, double x, double y, double z, float pitch, float yaw);
	
	public void causeSound(Sound sound, SoundCategory category, float volume, float pitch);
	
	public ViewerSet<Entity, Player> getViewers();
	
	public void sendMetadata(Player player);
	
}
