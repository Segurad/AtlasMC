package de.atlasmc.world;

import java.util.List;

import de.atlasmc.Effect;
import de.atlasmc.Location;
import de.atlasmc.Particle;
import de.atlasmc.SimpleLocation;
import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import de.atlasmc.atlasnetwork.server.LocalServer;
import de.atlasmc.block.Block;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.event.entity.EntitySpawnEvent;
import de.atlasmc.tick.Tickable;

public interface World extends Tickable {

	public List<Entity> getEntities();
	
	public <T extends Entity> List<T> getEntitiesByClass(Class<T> clazz);

	@SuppressWarnings("unchecked")
	public List<Entity> getEntitesByClasses(Class<? extends Entity>... classes);

	public String getName();
	
	public LocalServer getServer();

	public default Entity spawnEntity(EntityType type, SimpleLocation loc) {
		return spawnEntity(type, loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
	}
	
	public default Entity spawnEntity(EntityType type, double x, double y, double z) {
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
	public Entity spawnEntity(EntityType type, double x, double y, double z, float pitch, float yaw);
	
	public default boolean spawnEntity(Entity entity, SimpleLocation loc) {
		return spawnEntity(entity, loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
	}
	
	public default boolean spawnEntity(Entity entity, double x, double y, double z) {
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
	public boolean spawnEntity(Entity entity, double x, double y, double z, float pitch, float yaw); 
	
	/**
	 * Returns the next free entityID
	 * @return entityID
	 */
	int getEntityID();

	public Block getHighestBlockAt(int x, int z);

	public int getHighestBlockYAt(int x, int z);
	
	/**
	 * Returns the World's age in ticks
	 * @return the world age
	 */
	public long getAge();

	/**
	 * Returns the current Time of this World 
	 * @return the world time
	 */
	public long getTime();

	public void playEffect(SimpleLocation loc, Effect effect, Object data, int radius);

	public void spawnParticle(Particle particle, SimpleLocation loc, int amount);

	/**
	 * Returns the spawn Location of this world
	 * @return the spawn location
	 */
	public Location getSpawnLocation();
	
	/**
	 * Copies all spawn Location coordinates to the given Location
	 * @param loc the given Location
	 * @return the given Location
	 */
	public Location getSpawnLocation(Location loc);

	public void playSound(SimpleLocation loc, Sound sound, SoundCategory category, float volume, float pitch);

	public void playSound(SimpleLocation loc, String sound, SoundCategory category, float volume, float pitch);
	
	public void playSound(Entity entity, Sound sound, SoundCategory category, float volume, float pitch);
	
	public boolean hasFlag(WorldFlag flag);
	
	public void addFlag(WorldFlag flag);
	
	public void removeFlag(WorldFlag flag);

	public default Block getBlock(SimpleLocation loc) {
		return getBlock(loc.getBlockX(), loc.getBlockY(), loc.getBlockZ());
	}
	
	public Block getBlock(int x, int y, int z);
	
	public BlockData getBlockData(int x, int y, int z);
	
	public Entity getEntity(int entityID);
	
	/**
	 * Return the Chunk at the given x and z coordinates
	 * @param x of the chunk
	 * @param z of the chunk
	 * @return the chunk
	 */
	public Chunk getChunk(int x, int z);
	
	/**
	 * Returns the chunk at the given x and z coordinates or null if not loaded
	 * @param x
	 * @param z
	 * @param load if the chunk should be loaded
	 * @return chunk or null
	 */
	public Chunk getChunk(int x, int z, boolean load);
	
	/**
	 * Returns the Chunk at this location
	 * @param loc the chunk location
	 * @return chunk
	 */
	public Chunk getChunk(SimpleLocation loc);
	
	/**
	 * Returns the Chunk at this location or null if not loaded
	 * @param loc
	 * @param load if the chunk should be loaded
	 * @return chunk or null
	 */
	public Chunk getChunk(SimpleLocation loc, boolean load);

}
