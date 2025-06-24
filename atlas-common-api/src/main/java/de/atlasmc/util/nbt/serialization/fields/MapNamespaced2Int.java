package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.Function;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;

public class MapNamespaced2Int<T, K extends Namespaced> extends NBTField<T> {

	private final Function<T, Object2IntMap<K>> mapSupplier;
	private final Function<String, K> keySupplier;
	
	public MapNamespaced2Int(CharSequence key, Function<T, Object2IntMap<K>> mapSupplier, Function<String, K> keySupplier) {
		super(key, TagType.COMPOUND, false);
		this.mapSupplier = mapSupplier;
		this.keySupplier = keySupplier;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		final Object2IntMap<K> map = mapSupplier.apply(value);
		writer.writeCompoundTag(key);
		for (Entry<K> entry : map.object2IntEntrySet()) {
			K key = entry.getKey(); 
			NamespacedKey nkey = context.clientSide ? key.getClientKey() : key.getNamespacedKey();
			writer.writeIntTag(nkey.toString(), entry.getIntValue());
		}
		writer.writeEndTag();
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		final Object2IntMap<K> map = mapSupplier.apply(value);
		reader.readNextEntry();
		while (reader.getType() != TagType.TAG_END) {
			map.put(keySupplier.apply(reader.getFieldName().toString()), reader.readIntTag());
		}
		reader.readNextEntry();
	}

}
