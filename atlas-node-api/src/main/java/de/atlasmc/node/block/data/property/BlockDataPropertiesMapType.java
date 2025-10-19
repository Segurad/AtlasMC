package de.atlasmc.node.block.data.property;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.codec.type.ObjectType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

@Singleton
final class BlockDataPropertiesMapType extends ObjectType<Map<BlockDataProperty<?>, Object>> {

	private static final BlockDataPropertiesMapType INSTANCE = new BlockDataPropertiesMapType();
	
	public static BlockDataPropertiesMapType getInstance() {
		return INSTANCE;
	}
	
	private BlockDataPropertiesMapType() {
		// singleton
	}

	@Override
	public boolean serialize(CharSequence key, Map<BlockDataProperty<?>, Object> value, NBTWriter writer, CodecContext context) throws IOException {
		writer.writeCompoundTag(key);
		for (Entry<BlockDataProperty<?>, ?> entry : value.entrySet()) {
			@SuppressWarnings("unchecked")
			BlockDataProperty<Object> property = (BlockDataProperty<Object>) entry.getKey();
			property.toNBT(entry.getValue(), writer, !context.clientSide);
		}
		writer.writeEndTag();
		return true;
	}

	@Override
	public Map<BlockDataProperty<?>, Object> deserialize(Map<BlockDataProperty<?>, Object> value, NBTReader reader, CodecContext context) throws IOException {
		reader.readNextEntry();
		while (reader.getType() != TagType.TAG_END) {
			CharSequence key = reader.getFieldName();
			TagType type = reader.getType();
			BlockDataProperty<?> property = BlockDataProperty.getProperty(key, type);
			if (property == null) {
				reader.skipTag();
				continue;
			}
			value.put(property, property.fromNBT(reader));
		}
		reader.readNextEntry();
		return value;
	}

	@Override
	public List<TagType> getTypes() {
		return COMPOUND;
	}

}
