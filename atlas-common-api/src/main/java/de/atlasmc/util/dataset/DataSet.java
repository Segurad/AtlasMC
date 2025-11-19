package de.atlasmc.util.dataset;

import java.util.Collection;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.registry.ProtocolRegistryValue;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.tag.Tag;

public interface DataSet<T extends Namespaced> extends Iterable<T> {
	
	Collection<T> values();
	
	boolean contains(T value);
	
	int size();
	
	boolean isEmpty();
	
	static <T extends Namespaced> NBTCodec<DataSet<T>> nbtCodec(RegistryKey<T> key) {
		return new DataSetNBTCodec<>(key);
	}
	
	static <T extends ProtocolRegistryValue> StreamCodec<DataSet<T>> streamCodec(RegistryKey<T> key) {
		return new DataSetStreamCodec<>(key);
	}
	
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

}
