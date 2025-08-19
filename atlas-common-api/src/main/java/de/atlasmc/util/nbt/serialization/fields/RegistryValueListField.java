package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.registry.Registry;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class RegistryValueListField<T, K extends Namespaced> extends AbstractCollectionField<T, List<K>> {

	private final RegistryKey<K> registry;
	
	public RegistryValueListField(CharSequence key, ToBooleanFunction<T> has, Function<T, List<K>> get, RegistryKey<K> registry) {
		super(key, LIST, has, get, true);
		this.registry = registry;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		if (has != null && !has.applyAsBoolean(value))
			return true;
		final List<K> list = get.apply(value);
		final int size = list.size();
		if (size == 0)
			return true;
		writer.writeListTag(key, TagType.STRING, size);
		for (int i = 0; i < size; i++) {
			K v = list.get(i);
			NamespacedKey key = context.clientSide ? v.getClientKey() : v.getNamespacedKey();
			writer.writeNamespacedKey(null, key);
		}
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		final TagType listType = reader.getListType();
		if (listType == TagType.TAG_END || reader.getNextPayload() == 0) {
			reader.readNextEntry();
			reader.readNextEntry();
			return;
		}
		if (listType != TagType.STRING)
			throw new NBTException("Expected list of type STRING but was: " + listType);
		final Registry<K> registry = this.registry.get();
		final List<K> list = get.apply(value);
		reader.readNextEntry();
		while (reader.getRestPayload() > 0) {
			K v = registry.get(reader.readStringTag());
			if (v == null)
				continue;
			list.add(v);
		}
		reader.readNextEntry();
	}

}
