package de.atlasmc.world;

import java.util.List;

import de.atlasmc.block.Block;
import de.atlasmc.entity.Entity;
import de.atlasmc.tick.Tickable;

public interface ChunkProvider extends Tickable {

	public List<Entity> getEntities();

	public <T extends Entity> List<T> getEntitiesByClass(Class<T> clazz);

	public List<Entity> getEntitesByClasses(Class<? extends Entity>[] classes);

	public int getHighestBlockYAt(int x, int z);

	public Block getBlock(int x, int y, int z);

	public Entity getEntity(int entityID);

	/**
	 * 
	 * @param x chunk coordinate
	 * @param z chunk coordinate
	 * @return
	 */
	public Chunk getChunk(int x, int z);

}
