package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class MapNamespacedType2Type<T, K, V> extends AbstractCollectionField<T, Map<K, V>> {

	private final NBTSerializationHandler<V> handler;
	private final Function<V, K> getKey;
	
	public MapNamespacedType2Type(CharSequence key, ToBooleanFunction<T> has, Function<T, Map<K, V>> getMap, NBTSerializationHandler<V> handler, Function<V, K> getKey) {
		super(key, COMPOUND, has, getMap, true);
		this.handler = handler;
		this.getKey = getKey;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		if (has != null && !has.applyAsBoolean(value))
			return true;
		final Map<K, V> map = get.apply(value);
		writer.writeCompoundTag(key);
		for (V entry : map.values()) {
			handler.serialize(entry, writer, context);
		}
		writer.writeEndTag();
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		final Map<K, V> map = get.apply(value);
		reader.readNextEntry();
		while (reader.getType() != TagType.TAG_END) {
			V entry = handler.deserialize(reader);
			map.put(getKey.apply(entry), entry);
		}
		reader.readNextEntry();
	}

}
