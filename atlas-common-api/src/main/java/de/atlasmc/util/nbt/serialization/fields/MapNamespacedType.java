package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class MapNamespacedType<T, K extends Namespaced> extends AbstractCollectionField<T, Map<NamespacedKey, K>> {

	private final NBTSerializationHandler<K> handler;
	
	public MapNamespacedType(CharSequence key, ToBooleanFunction<T> has, Function<T, Map<NamespacedKey, K>> getMap, NBTSerializationHandler<K> handler) {
		super(key, COMPOUND, has, getMap, true);
		this.handler = handler;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		if (has != null && !has.applyAsBoolean(value))
			return true;
		final Map<NamespacedKey, K> map = get.apply(value);
		writer.writeCompoundTag(key);
		for (K entry : map.values()) {
			handler.serialize(entry, writer, context);
		}
		writer.writeEndTag();
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		final Map<NamespacedKey, K> map = get.apply(value);
		reader.readNextEntry();
		while (reader.getType() != TagType.TAG_END) {
			K entry = handler.deserialize(reader);
			map.put(entry.getNamespacedKey(), entry);
		}
		reader.readNextEntry();
	}

}
