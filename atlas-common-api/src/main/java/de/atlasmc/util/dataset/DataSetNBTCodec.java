package de.atlasmc.util.dataset;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.CodecTags;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.registry.Registry;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.tag.Tag;
import de.atlasmc.tag.Tags;
import de.atlasmc.util.codec.CodecContext;

final class DataSetNBTCodec<T extends Namespaced> implements NBTCodec<DataSet<T>> {

	private final RegistryKey<T> registry;
	
	public DataSetNBTCodec(RegistryKey<T> registry) {
		this.registry = Objects.requireNonNull(registry);
	}
	
	@Override
	public Class<?> getType() {
		return DataSet.class;
	}

	@Override
	public List<TagType> getTags() {
		return CodecTags.LIST_STRING;
	}

	@Override
	public boolean serialize(CharSequence key, DataSet<T> value, NBTWriter writer, CodecContext context) throws IOException {
		final int size = value.size();
		if (size == 0)
			return true;
		if (value instanceof TagDataSet<T> tags) {
			Tag<T> tag = tags.getTag();
			NamespacedKey k = context.clientSide ? tag.getClientKey() : tag.getNamespacedKey();
			writer.writeStringTag(key, "#" + k);
		} else if (value instanceof SingleValueDataSet<T> single) {
			T v = single.getValue();
			writer.writeNamespacedKey(key, context.clientSide ? v.getClientKey() : v.getNamespacedKey());
		} else {
			writer.writeListTag(key, TagType.STRING, size);
			if (context.clientSide) {
				for (T v : value.values())
					writer.writeNamespacedKey(null, v.getClientKey());
			} else {
				for (T v : value.values())
					writer.writeNamespacedKey(null, v.getNamespacedKey());
			}
		}
		return true;
	}

	@Override
	public DataSet<T> deserialize(DataSet<T> value, NBTReader reader, CodecContext context) throws IOException {
		final TagType type = reader.getType();
		switch (type) {
		case STRING: {
			String key = reader.readStringTag();
			if (key.charAt(0) == '#') {
				Tag<T> tag = Tags.getTag(NamespacedKey.of(key.substring(1)));
				return tag == null ? DataSet.of() : DataSet.of(tag);
			} else {
				T entry = registry.getValue(key);
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
			ArrayList<T> types = null;
			final Registry<T> registry = this.registry.get();
			while (reader.getRestPayload() > 0) {
				String key = reader.readStringTag();
				T v = registry.get(key);
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
