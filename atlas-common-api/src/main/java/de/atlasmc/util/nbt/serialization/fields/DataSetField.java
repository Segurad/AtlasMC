package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.ArrayList;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.registry.Registry;
import de.atlasmc.tag.Tag;
import de.atlasmc.tag.Tags;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.dataset.SingleValueDataSet;
import de.atlasmc.util.dataset.TagDataSet;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class DataSetField<T, K extends Namespaced> extends AbstractObjectField<T, DataSet<K>> {

	private final Registry<K> registry;
	
	public DataSetField(CharSequence key, Function<T, DataSet<K>> get, BiConsumer<T, DataSet<K>> set, Registry<K> registry) {
		super(key, LIST_STRING, get, set, true);
		this.registry = registry;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		final DataSet<K> data = get.apply(value);
		if (data == null)
			return true;
		final int size = data.size();
		if (size == 0)
			return true;
		if (data instanceof TagDataSet<K> tags) {
			Tag<K> tag = tags.getTag();
			NamespacedKey key = context.clientSide ? tag.getClientKey() : tag.getNamespacedKey();
			writer.writeStringTag(this.key, "#" + key);
		} else if (data instanceof SingleValueDataSet<K> single) {
			K v = single.getValue();
			writer.writeNamespacedKey(key, context.clientSide ? v.getClientKey() : v.getNamespacedKey());
		} else {
			writer.writeListTag(key, TagType.STRING, size);
			if (context.clientSide) {
				for (K v : data.values())
					writer.writeNamespacedKey(null, v.getClientKey());
			} else {
				for (K v : data.values())
					writer.writeNamespacedKey(null, v.getNamespacedKey());
			}
		}
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		final TagType type = reader.getType();
		switch (type) {
		case STRING: {
			String key = reader.readStringTag();
			if (key.charAt(0) == '#') {
				Tag<K> tag = Tags.getTag(NamespacedKey.of(key.substring(1)));
				if (tag == null) {
					set.accept(value, DataSet.of());
				} else {
					set.accept(value, DataSet.of(tag));
				}
			} else {
				K entry = registry.get(key);
				if (entry == null) {
					set.accept(value, DataSet.of());
				} else {
					set.accept(value, DataSet.of(entry));
				}
			}
		}
		case LIST:
			TagType listType = reader.getListType();
			if (listType == TagType.TAG_END || reader.getNextPayload() == 0) {
				reader.readNextEntry();
				reader.readNextEntry();
				return;
			}
			if (listType != TagType.STRING)
				throw new NBTException("Expected list of type STRING but was: " + listType);
			reader.readNextEntry();
			ArrayList<K> types = new ArrayList<K>();
			while (reader.getRestPayload() > 0) {
				String key = reader.readStringTag();
				types.add(registry.get(key));
			}
			reader.readNextEntry();
			if (types.isEmpty()) {
				set.accept(value, DataSet.of());
			} else {
				set.accept(value, DataSet.of(types));
			}
		default:
			throw new NBTException("Unexpected tag type: " + type);
		}
	}

}
