package de.atlasmc.block.data.property;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;
import de.atlasmc.util.nbt.serialization.fields.AbstractCollectionField;

final class BlockDataPropertiesMapField<T> extends AbstractCollectionField<T, Map<BlockDataProperty<?>, Object>> {

	public BlockDataPropertiesMapField(CharSequence key, ToBooleanFunction<T> has, Function<T, Map<BlockDataProperty<?>, Object>> getCollection) {
		super(key, COMPOUND, has, getCollection, true);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		if (has != null && !has.applyAsBoolean(value))
			return true;
		final Map<BlockDataProperty<?>, Object> map = get.apply(value);
		if (map.isEmpty())
			return true;
		writer.writeCompoundTag(key);
		for (Entry<BlockDataProperty<?>, ?> entry : map.entrySet()) {
			@SuppressWarnings("unchecked")
			BlockDataProperty<Object> property = (BlockDataProperty<Object>) entry.getKey();
			property.toNBT(entry.getValue(), writer, !context.clientSide);
		}
		writer.writeEndTag();
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		final Map<BlockDataProperty<?>, Object> properties = get.apply(value);
		reader.readNextEntry();
		while (reader.getType() != TagType.TAG_END) {
			CharSequence key = reader.getFieldName();
			TagType type = reader.getType();
			BlockDataProperty<?> property = BlockDataProperty.getProperty(key, type);
			if (property == null) {
				reader.skipTag();
				continue;
			}
			properties.put(property, property.fromNBT(reader));
		}
		reader.readNextEntry();
	}

}
