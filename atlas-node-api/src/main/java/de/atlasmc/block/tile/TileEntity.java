package de.atlasmc.block.tile;

import java.io.IOException;

import de.atlasmc.SimpleLocation;
import de.atlasmc.block.BlockType;
import de.atlasmc.util.annotation.InternalAPI;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.world.Chunk;
import de.atlasmc.world.World;

public interface TileEntity extends NBTHolder, Cloneable {

	public static final CharKey NBT_ID = CharKey.literal("id");
	
	TileEntity clone();
	
	BlockType getType();
	
	void setType(BlockType material);
	
	/**
	 * Returns the <u><b>relative</b></u> Location of this Tile in the Chunk
	 * @return location
	 */
	SimpleLocation getLocation();
	
	int getX();
	
	int getY();
	
	int getZ();
	
	Chunk getChunk();
	
	World getWorld();
	
	@InternalAPI
	void setLocation(Chunk chunk, int x, int y, int z);

	int getID();
	
	public static TileEntity getFromNBT(NBTReader reader) throws IOException {
		if (reader.getType() == TagType.TAG_END) { // Empty Tag 
			reader.readNextEntry();
			return null;
		}
		String rawType = null;
		if (!NBT_ID.equals(reader.getFieldName())) {
			reader.mark();
			reader.search(NBT_ID);
			rawType = reader.readStringTag();
			reader.reset();
		} else {
			rawType = reader.readStringTag();
		}
		if (rawType == null) {
			throw new NBTException("NBT did not container id field!");
		}
		BlockType material = BlockType.get(rawType);
		if (material == null) {
			throw new NBTException("No type found with name: " + rawType);
		}
		TileEntity tile = material.createTileEntity();
		if (tile == null) {
			throw new NBTException("Failed to create tile from type: " + material.getNamespacedKeyRaw());
		}
		tile.fromNBT(reader);
		return tile;
	}
	
}
