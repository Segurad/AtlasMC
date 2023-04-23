package de.atlascore.world;

import java.util.List;

import de.atlasmc.block.Block;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.entity.Entity;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkProvider;

/**
 * ChunkProvider for quicker chunk access but no dynamic world size<br>
 * Stores all chunks in a defined area with calculable access
 */
public class CoreFixedChunkProvider implements ChunkProvider {

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Entity> getEntities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Entity> List<T> getEntitiesByClass(Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Entity> getEntitesByClasses(Class<? extends Entity>[] classes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getHighestBlockYAt(int x, int z) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Block getBlock(int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entity getEntity(int entityID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Chunk getChunk(int x, int z, boolean load) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public BlockData getBlockData(int x, int y, int z) {
		// TODO Auto-generated method stub
		return null;
	}

}
