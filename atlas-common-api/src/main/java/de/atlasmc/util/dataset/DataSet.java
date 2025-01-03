package de.atlasmc.util.dataset;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.registry.Registry;
import de.atlasmc.tag.Tag;
import de.atlasmc.tag.Tags;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public interface DataSet<T extends Namespaced> {
	
	Collection<T> values();
	
	boolean contains(T value);
	
	int size();
	
	boolean isEmpty();
	
	static <T extends Namespaced> DataSet<T> of() {
		@SuppressWarnings("unchecked")
		DataSet<T> set = (DataSet<T>) EmptyDataSet.INSTANCE;
		return set;
	}
	
	static <T extends Namespaced> TagDataSet<T> of(Tag<T> tag) {
		return new TagDataSet<>(tag);
	}
	
	static <T extends Namespaced> DataSet<T> of(T value) {
		if (value == null)
			return of();
		return new SingleValueDataSet<>(value);
	}
	
	@SafeVarargs
	static <T extends Namespaced> DataSet<T> of(T... values) {
		if (values == null)
			throw new IllegalArgumentException("Values can not be null!");
		if (values.length == 0)
			return of();
		if (values.length == 1)
			return of(values[1]);
		return new CollectionDataSet<>(values);
	}
	
	static <T extends Namespaced> DataSet<T> of(Collection<T> values) {
		if (values == null)
			throw new IllegalArgumentException("Values can not be null!");
		if (values.isEmpty())
			return of();
		if (values.size() == 1)
			return of(values.iterator().next());
		return new CollectionDataSet<>(values);
	}
	
	@NotNull
	static <T extends Namespaced> DataSet<T> getFromNBT(Registry<T> registry, NBTReader reader) throws IOException {
		if (registry == null)
			throw new IllegalArgumentException("Registry can not be null!");
		TagType type = reader.getType();
		switch (type) {
		case STRING: {
			String key = reader.readStringTag();
			if (key.charAt(0) == '#') {
				Tag<T> tag = Tags.getTag(NamespacedKey.of(key.substring(1)));
				if (tag == null)
					return DataSet.of();
				return DataSet.of(tag);
			} else {
				T entry = registry.get(key);
				if (entry == null)
					return DataSet.of();
				return DataSet.of(entry);
			}
		}
		case LIST:
			reader.readNextEntry();
			ArrayList<T> types = new ArrayList<T>();
			while (reader.getRestPayload() > 0) {
				String key = reader.readStringTag();
				types.add(registry.get(key));
			}
			reader.readNextEntry();
			if (types.isEmpty())
				return DataSet.of();
			return DataSet.of(types);
		default:
			throw new NBTException("Unexpected type: " + type);
		}
	}
	
	static <T extends Namespaced> void toNBT(CharSequence key, DataSet<T> set, NBTWriter writer, boolean systemData) throws IOException {
		if (set instanceof TagDataSet tags) {
			writer.writeStringTag(key, "#" + tags.getTag().getNamespacedKeyRaw());
		} else if (set instanceof SingleValueDataSet<T> single) {
			T value = single.getValue();
			writer.writeNamespacedKey(key, systemData ? value.getNamespacedKey() : value.getClientKey());
		} else {
			writer.writeListTag(key, TagType.STRING, set.size());
			if (systemData) {
				for (T value : set.values())
					writer.writeNamespacedKey(key, value.getNamespacedKey());
			} else {
				for (T value : set.values())
					writer.writeNamespacedKey(key, value.getClientKey());
			}
		}
	}

}
