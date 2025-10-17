package de.atlasmc.util.nbt.codec.constructor;

import java.io.IOException;
import java.util.function.Function;

import de.atlasmc.registry.RegistryKey;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class FieldKeyConstructor<T, K> extends AbstractRegistryConstructor<T, K> {

	public FieldKeyConstructor(RegistryKey<K> registry, Function<K, T> constructor) {
		super(registry, constructor);
	}

	@Override
	public T construct(NBTReader reader, CodecContext context) throws IOException {
		CharSequence key = reader.getFieldName();
		K regValue = registry.getValue(key);
		return constructor.apply(regValue);
	}

	@Override
	public void serialize(T value, NBTWriter writer, CodecContext context) throws IOException {
		// not required
	}

}
