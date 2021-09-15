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
import de.atlasmc.tick.Tickable;

public interface World extends Tickable {

	public List<Entity> getEntities();
	public <T extends Entity> List<T> getEntitiesByClass(Class<T> clazz);

	@SuppressWarnings("unchecked")
	public List<Entity> getEntitesByClasses(Class<? extends Entity>... classes);

	public String getName();
	
	public LocalServer getServer();

	public Entity spawnEntity(SimpleLocation loc, EntityType type);

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
	 * Returns the Chunk at this Location
	 * @param loc the chunk location
	 * @return the chunk
	 */
	public Chunk getChunk(SimpleLocation loc);
	
	/**
	 * Updates the Block at this position for Players
	 * @param x world coordinate
	 * @param y world coordinate
	 * @param z world coordinate
	 */
	public void sendUpdate(int x, int y, int z);
	
	/**
	 * Updates the Block at this position for Players
	 * @param chunk of Blocks
	 * @param x chunk coordinate
	 * @param y chunk coordinate
	 * @param z chunk coordinate
	 */
	public void sendUpdate(Chunk chunk, int x, int y, int z);

}
