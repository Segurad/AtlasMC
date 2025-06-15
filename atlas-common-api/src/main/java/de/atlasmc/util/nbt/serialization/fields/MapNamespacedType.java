package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.Map;
import java.util.function.Function;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class MapNamespacedType<T, K extends Namespaced & NBTSerializable> extends NBTField<T> {

	private final Function<T, Map<NamespacedKey, K>> mapSupplier;
	private final NBTSerializationHandler<K> handler;
	
	public MapNamespacedType(CharSequence key, Function<T, Map<NamespacedKey, K>> mapSupplier, NBTSerializationHandler<K> handler) {
		super(key, TagType.COMPOUND, false);
		this.mapSupplier = mapSupplier;
		this.handler = handler;
	}

	@Override
	public void serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		final Map<NamespacedKey, K> map = mapSupplier.apply(value);
		writer.writeCompoundTag(key);
		for (K entry : map.values()) {
			handler.serialize(entry, writer, context);
		}
		writer.writeEndTag();
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		final Map<NamespacedKey, K> map = mapSupplier.apply(value);
		reader.readNextEntry();
		while (reader.getType() != TagType.TAG_END) {
			K entry = handler.deserialize(reader);
			map.put(entry.getNamespacedKey(), entry);
		}
		reader.readNextEntry();
	}

}
