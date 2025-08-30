package de.atlasmc.entity;

import java.util.List;
import java.util.UUID;

import org.joml.Vector3d;

import de.atlasmc.Location;
import de.atlasmc.Nameable;
import de.atlasmc.SimpleLocation;
import de.atlasmc.server.LocalServer;
import de.atlasmc.sound.SoundEmitter;
import de.atlasmc.tick.Tickable;
import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumValueCache;
import de.atlasmc.util.ViewerSet;
import de.atlasmc.util.annotation.ThreadSafe;
import de.atlasmc.util.annotation.UnsafeAPI;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.World;
import de.atlasmc.world.entitytracker.EntityPerception;

public interface Entity extends NBTSerializable, Nameable, Tickable, SoundEmitter {
	
	public static final NBTSerializationHandler<Entity>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Entity.class)
					.searchKeyConstructor("id", EntityType.REGISTRY_KEY, EntityType::createEntity, Entity::getType)
					.shortField("Air", Entity::getAirTicks, Entity::setAirTicks, (short) 300)
					.include(Nameable.NBT_HANDLER)
					.boolField("CustomNameVisible", Entity::isCustomNameVisible, Entity::setCustomNameVisible, false)
					// data
					.doubleField("fall_distance", Entity::getFallDistance, Entity::setFallDistance, 0)
					.shortField("Fire", Entity::getFireTicks, Entity::setFireTicks, (short) 0)
					.boolField("Glowing", Entity::isGlowing, Entity::setGlowing, false)
					.boolField("HasVisualFire", Entity::hasVisualFire, Entity::setVisualFire, false)
					.boolField("Invulnerable", Entity::isInvulnerable, Entity::setInvulnerable, false)
					.vector3d("Motion", Entity::getVelocityUnsafe, Entity::setVelocity)
					.boolField("NoGravity", Entity::hasNoGravity, Entity::setNoGravity, false)
					.boolField("OnGround", Entity::isOnGround, Entity::setOnGround, true)
					// Passengers (not implemented because recursive)
					.intField("PortalCooldown", Entity::getPortalCooldown, Entity::setPortalCooldown, 0)
					.vector3d("Pos", Entity::getLocationUnsafe, Entity::setLocation)
					// Rotation
					.boolField("Silent", Entity::isSilent, Entity::setSilent)
					.stringListField("Tags", Entity::hasScoreboardTags, Entity::getScoreboardTags)
					.intField("TicksFrozen", Entity::getFreezeTicks, Entity::setFreezeTicks, 0)
					.uuid("UUID", Entity::getUUID, Entity::setUUID)
					.build();
	
	void addScoreboardTag(String tag);
	
	int getAirTicks();
	
	double getFallDistance();
	
	short getFireTicks();
	
	boolean hasVisualFire();
	
	void setVisualFire(boolean fire);
	
	/**
	 * 
	 * @return the internal entity id
	 */
	int getID();
	
	@UnsafeAPI
	Location getLocationUnsafe();
	
	Location getLocation();
	
	void setLocation(Vector3d loc);
	
	void setLocation(SimpleLocation loc);
	
	Location getLocation(Location loc);
	
	SimpleLocation getLocation(SimpleLocation loc);
	
	Pose getPose();
	
	List<String> getScoreboardTags();
	
	LocalServer getServer();
	
	EntityType getType();
	
	/**
	 * Returns the UUID of this Entity
	 * @return UUID
	 */
	UUID getUUID();
	
	@UnsafeAPI
	Vector3d getVelocityUnsafe();
	
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
	
	boolean hasNoGravity();
	
	boolean hasScoreboardTags();
	
	boolean hasVelocity();

	boolean isCustomNameVisible();
	
	boolean isFlyingWithElytra();
	
	boolean isGlowing();
	
	void setGlowing(boolean glowing);
	
	boolean isInvisible();
	
	void setInvisible(boolean invisible);
	
	boolean isOnGround();
	
	void setOnGround(boolean onGround);
	
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

	void setFallDistance(double distance);
	
	void setFireTicks(int ticks);


	void setNoGravity(boolean gravity);
	
	void setInvulnerable(boolean invulnerable);

	void setPortalCooldown(int cooldown);

	void setPose(Pose pose);

	void setSilent(boolean silent);

	void setUUID(UUID uuid);
	
	int getFreezeTicks();
	
	void setFreezeTicks(int ticks);
	
	default void teleport(SimpleLocation loc) {
		teleport(loc, null, 0);
	}
	
	default void teleport(SimpleLocation loc, int flags) {
		teleport(loc, null, flags);
	}
	
	default void teleport(SimpleLocation loc, Vector3d velocity) {
		teleport(loc, velocity, 0);
	}
	
	/**
	 * Teleports the entity to the given position and applies the given velocity.
	 * If the velocity is null the entities current velocity is used
	 * @param loc
	 * @param velocity
	 * @param flags
	 */
	void teleport(SimpleLocation loc, Vector3d velocity, int flags);
	
	default void teleport(double x, double y, double z) {
		teleport(x, y, z, 0);
	}
	
	default void teleport(double x, double y, double z, float pitch, float yaw) {
		teleport(x, y, z, pitch, yaw, 0);
	}
	
	void teleport(double x, double y, double z, int flags);
	
	void teleport(double x, double y, double z, float pitch, float yaw, int flags);
	
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
	
	ViewerSet<Entity, Player> getViewers();
	
	EntityPerception getPerception();
	
	void setPerception(EntityPerception perception);
	
	double getPerceptionDistance();
	
	void setPerceptionDistance(double distance);
	
	void setTicking(boolean ticking);

	boolean isTicking();
	
	@Override
	default NBTSerializationHandler<? extends Entity> getNBTHandler() {
		return NBT_HANDLER;
	}
	
	public static enum Animation implements EnumID, EnumValueCache {
		
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
				VALUES = List.of(values());
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
	
	public static enum Pose implements EnumID, EnumValueCache {
		
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
				VALUES = List.of(values());
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
	
}
