package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class MapString2TypeField<T, K> extends AbstractCollectionField<T, Map<String, K>> {

	private final NBTSerializationHandler<K> handler;
	
	public MapString2TypeField(CharSequence key, ToBooleanFunction<T> has, Function<T, Map<String, K>> getMap, NBTSerializationHandler<K> handler) {
		super(key, COMPOUND, has, getMap, true);
		this.handler = handler;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		if (has != null && !has.applyAsBoolean(value))
			return true;
		final Map<String, K> map = get.apply(value);
		writer.writeCompoundTag(key);
		for (Entry<String, K> entry : map.entrySet()) {
			writer.writeCompoundTag(entry.getKey());
			handler.serialize(entry.getValue(), writer, context);
			writer.writeEndTag();
		}
		writer.writeEndTag();
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		final Map<String, K> map = get.apply(value);
		reader.readNextEntry();
		while (reader.getType() != TagType.TAG_END) {
			String key = reader.getFieldName().toString();
			K entry = handler.deserialize(reader);
			map.put(key, entry);
		}
		reader.readNextEntry();
	}

}
