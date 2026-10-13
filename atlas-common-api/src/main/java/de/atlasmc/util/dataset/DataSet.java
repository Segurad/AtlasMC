package de.atlasmc.util.dataset;

import java.util.Collection;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.registry.ProtocolRegistryValue;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.tag.Tag;
import de.atlasmc.util.annotation.NotNull;

/**
 * Immutable set of values
 * @param <T>
 */
public interface DataSet<T extends Namespaced> extends Collection<T> {
	
	@NotNull
	static <T extends Namespaced> NBTCodec<DataSet<T>> nbtCodec(RegistryKey<T> key) {
		return new DataSetNBTCodec<>(key);
	}
	
	@NotNull
	static <T extends ProtocolRegistryValue> StreamCodec<DataSet<T>> streamCodec(RegistryKey<T> key) {
		return new DataSetStreamCodec<>(key);
	}
	
	@NotNull
	static <T extends Namespaced> DataSet<T> of() {
		@SuppressWarnings("unchecked")
		DataSet<T> set = (DataSet<T>) EmptyDataSet.INSTANCE;
		return set;
	}
	
	@NotNull
	static <T extends Namespaced> TagDataSet<T> of(Tag<T> tag) {
		return new TagDataSet<>(tag);
	}
	
	@NotNull
	static <T extends Namespaced> DataSet<T> of(T value) {
		if (value == null)
			return of();
		return new SingleValueDataSet<>(value);
	}
	
	@SafeVarargs
	static <T extends Namespaced> DataSet<T> of(T... values) {
		if (values.length == 0) // implicit null check
			return of();
		if (values.length == 1)
			return of(values[0]);
		return new CollectionDataSet<>(values);
	}
	
	@NotNull
	static <T extends Namespaced> DataSet<T> of(Collection<T> values) {
		if (values.isEmpty()) // implicit null check
			return of();
		if (values.size() == 1)
			return of(values.iterator().next());
		return new CollectionDataSet<>(values);
	}

}
