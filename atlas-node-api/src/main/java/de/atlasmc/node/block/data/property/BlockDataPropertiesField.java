package de.atlasmc.node.block.data.property;

import java.io.IOException;
import java.util.List;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.codec.type.NBTField;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

final class BlockDataPropertiesField<T extends BlockData> extends NBTField<T> {

	public BlockDataPropertiesField(CharSequence key) {
		super(key, COMPOUND, true);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, CodecContext context) throws IOException {
		final List<BlockDataProperty<?>> properties = value.getProperties();
		if (properties.isEmpty()) {
			return true;
		}
		writer.writeCompoundTag(key);
		final int size = properties.size();
		for (int i = 0; i < size; i++) {
			BlockDataProperty<?> property = properties.get(i);
			property.dataToNBT(value, writer, !context.clientSide);
		}
		writer.writeEndTag();
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, CodecContext context) throws IOException {
		reader.readNextEntry();
		while (reader.getType() != TagType.TAG_END) {
			CharSequence key = reader.getFieldName();
			TagType type = reader.getType();
			BlockDataProperty<?> property = BlockDataProperty.getProperty(key, type);
			if (property == null) {
				reader.skipTag();
				continue;
			}
			property.dataFromNBT(value, reader);
		}
		reader.readNextEntry();
	}

}
