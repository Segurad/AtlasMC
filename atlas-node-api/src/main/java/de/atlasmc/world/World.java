package de.atlasmc.world;

import java.util.Collection;

import de.atlasmc.Location;
import de.atlasmc.SimpleLocation;
import de.atlasmc.block.Block;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.event.entity.EntitySpawnEvent;
import de.atlasmc.server.LocalServer;
import de.atlasmc.sound.SoundListener;
import de.atlasmc.tick.Tickable;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.world.entitytracker.EntityTracker;
import de.atlasmc.world.particle.ParticleType;

public interface World extends Tickable, SoundListener {

	@NotNull
	Collection<Entity> getEntities();
	
	@NotNull
	<T extends Entity> Collection<T> getEntitiesByClass(Class<T> clazz);

	@NotNull
	String getName();
	
	@NotNull
	LocalServer getServer();

	default Entity spawnEntity(EntityType type, SimpleLocation loc) {
		return spawnEntity(type, loc.x, loc.y, loc.z, loc.pitch, loc.yaw);
	}
	
	default Entity spawnEntity(EntityType type, double x, double y, double z) {
		return spawnEntity(type, x, y, z, 0, 0);
	}
	
	/**
	 * Creates a Entity and calls {@link EntitySpawnEvent} 
	 * if the event was successfully the entity will be spawned otherwise null will be returned
	 * @param type
	 * @param x
	 * @param y
	 * @param z
	 * @param pitch
	 * @param yaw
	 * @return Entity or null
	 */
	Entity spawnEntity(EntityType type, double x, double y, double z, float pitch, float yaw);
	
	default boolean spawnEntity(Entity entity, SimpleLocation loc) {
		return spawnEntity(entity, loc.x, loc.y, loc.z, loc.pitch, loc.yaw);
	}
	
	default boolean spawnEntity(Entity entity, double x, double y, double z) {
		return spawnEntity(entity, x, y, z, 0, 0);
	}
	
	/**
	 * Spawns a {@link Entity} in this World.<br>
	 * Will always return false if the Entity is not removed! 
	 * @param entity
	 * @param x
	 * @param y
	 * @param z
	 * @param pitch
	 * @param yaw
	 * @return true if spawning was successfully
	 */
	boolean spawnEntity(Entity entity, double x, double y, double z, float pitch, float yaw); 

	Block getHighestBlockAt(int x, int z);

	int getHighestBlockYAt(int x, int z);
	
	/**
	 * Returns the World's age in ticks
	 * @return the world age
	 */
	long getAge();

	/**
	 * Returns the current Time of this World 
	 * @return the world time
	 */
	long getTime();

	void playEffect(SimpleLocation loc, WorldEvent effect, Object data, int radius);

	void spawnParticle(ParticleType particle, SimpleLocation loc, int amount);

	/**
	 * Returns the spawn Location of this world
	 * @return the spawn location
	 */
	Location getSpawnLocation();
	
	/**
	 * Copies all spawn Location coordinates to the given Location
	 * @param loc the given Location
	 * @return the given Location
	 */
	Location getSpawnLocation(Location loc);
	
	boolean hasFlag(WorldFlag flag);
	
	void addFlag(WorldFlag flag);
	
	void removeFlag(WorldFlag flag);

	default Block getBlock(SimpleLocation loc) {
		return getBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}
	
	Block getBlock(int x, int y, int z);
	
	BlockData getBlockData(int x, int y, int z);
	
	Entity getEntity(int entityID);
	
	/**
	 * Return the Chunk at the given x and z coordinates
	 * @param x of the chunk
	 * @param z of the chunk
	 * @return the chunk
	 */
	@Nullable
	Chunk getChunk(int x, int z);
	
	/**
	 * Returns the chunk at the given x and z coordinates or null if not loaded
	 * @param x
	 * @param z
	 * @param load if the chunk should be loaded
	 * @return chunk or null
	 */
	@Nullable
	Chunk getChunk(int x, int z, boolean load);
	
	/**
	 * Returns the Chunk at this location
	 * @param loc the chunk location
	 * @return chunk
	 */
	@Nullable
	Chunk getChunk(SimpleLocation loc);
	
	/**
	 * Returns the Chunk at this location or null if not loaded
	 * @param loc
	 * @param load if the chunk should be loaded
	 * @return chunk or null
	 */
	@Nullable
	Chunk getChunk(SimpleLocation loc, boolean load);

	@NotNull
	Dimension getDimension();
	
	@NotNull
	EntityTracker getEntityTracker();

}
