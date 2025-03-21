package de.atlasmc.block.data;

import java.io.IOException;
import java.util.List;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.property.BlockDataProperty;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.NBTHolder;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;

public interface BlockData extends Cloneable, NBTHolder {

	public static final CharKey
	NBT_NAME = CharKey.literal("Name"),
	NBT_PROPERTIES = CharKey.literal("Properties");
	
	BlockData clone();
	
	BlockType getType();
	
	int getStateID();

	List<BlockDataProperty<?>> getProperties();
	
	static BlockData getFromNBT(NBTReader reader) throws IOException {
		if (reader.getType() == TagType.TAG_END) { // Empty Tag 
			reader.readNextEntry();
			return null;
		}
		String rawType = null;
		if (!NBT_NAME.equals(reader.getFieldName())) {
			reader.mark();
			reader.search(NBT_NAME);
			rawType = reader.readStringTag();
			reader.reset();
		} else {
			rawType = reader.readStringTag();
		}
		if (rawType == null) {
			throw new NBTException("NBT did not container id field!");
		}
		BlockType type = BlockType.get(rawType);
		if (type == null) {
			throw new NBTException("No type found with name: " + rawType);
		}
		BlockData data = type.createBlockData();
		if (data == null) {
			throw new NBTException("Failed to create data from type: " + type.getNamespacedKeyRaw());
		}
		data.fromNBT(reader);
		return data;
	}
	
}
