package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.registry.Registry;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.tag.Tag;
import de.atlasmc.tag.Tags;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.dataset.SingleValueDataSet;
import de.atlasmc.util.dataset.TagDataSet;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class DataSetType<K extends Namespaced> extends ObjectType<DataSet<K>> {

	private final RegistryKey<K> registry;
	
	public DataSetType(RegistryKey<K> registry) {
		this.registry = Objects.requireNonNull(registry);
	}

	@Override
	public List<TagType> getTypes() {
		return LIST_STRING;
	}

	@Override
	public boolean serialize(CharSequence key, DataSet<K> value, NBTWriter writer, CodecContext context) throws IOException {
		final int size = value.size();
		if (size == 0)
			return true;
		if (value instanceof TagDataSet<K> tags) {
			Tag<K> tag = tags.getTag();
			NamespacedKey k = context.clientSide ? tag.getClientKey() : tag.getNamespacedKey();
			writer.writeStringTag(key, "#" + k);
		} else if (value instanceof SingleValueDataSet<K> single) {
			K v = single.getValue();
			writer.writeNamespacedKey(key, context.clientSide ? v.getClientKey() : v.getNamespacedKey());
		} else {
			writer.writeListTag(key, TagType.STRING, size);
			if (context.clientSide) {
				for (K v : value.values())
					writer.writeNamespacedKey(null, v.getClientKey());
			} else {
				for (K v : value.values())
					writer.writeNamespacedKey(null, v.getNamespacedKey());
			}
		}
		return true;
	}

	@Override
	public DataSet<K> deserialize(DataSet<K> value, NBTReader reader, CodecContext context) throws IOException {
		final TagType type = reader.getType();
		switch (type) {
		case STRING: {
			String key = reader.readStringTag();
			if (key.charAt(0) == '#') {
				Tag<K> tag = Tags.getTag(NamespacedKey.of(key.substring(1)));
				return tag == null ? DataSet.of() : DataSet.of(tag);
			} else {
				K entry = registry.getValue(key);
				return entry == null ? DataSet.of() : DataSet.of(entry);
			}
		}
		case LIST:
			TagType listType = reader.getListType();
			if (listType == TagType.TAG_END || reader.getNextPayload() == 0) {
				reader.readNextEntry();
				reader.readNextEntry();
				return null;
			}
			if (listType != TagType.STRING)
				throw new NBTException("Expected list of type STRING but was: " + listType);
			reader.readNextEntry();
			ArrayList<K> types = null;
			final Registry<K> registry = this.registry.get();
			while (reader.getRestPayload() > 0) {
				String key = reader.readStringTag();
				K v = registry.get(key);
				if (types == null)
					types = new ArrayList<>();
				types.add(v);
			}
			reader.readNextEntry();
			return types == null ? DataSet.of() : DataSet.of(types);
		default:
			throw new NBTException("Unexpected tag type: " + type);
		}
	}

}
