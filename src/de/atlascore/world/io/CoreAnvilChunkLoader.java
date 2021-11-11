package de.atlascore.world.io;

import java.io.IOException;

import de.atlascore.world.CoreChunk;
import de.atlasmc.util.nbt.AbstractNBTBase;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.ChunkLoader;
import de.atlasmc.world.World;

public class CoreAnvilChunkLoader extends AbstractNBTBase implements ChunkLoader {
	
	private CoreChunk chunk;

	@Override
	public void saveChunk(Chunk chunk, NBTWriter writer) {
		// TODO Auto-generated method stub
	}

	@Override
	public Chunk loadChunk(World world, NBTReader reader) throws IOException {
		chunk = new CoreChunk(world);
		super.fromNBT(reader);
		return chunk;
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		throw new RuntimeException("Not implemented");		
	}

	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		// TODO Auto-generated method stub
		return null;
	}

}
