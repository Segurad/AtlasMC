package de.atlasmc.nbt.codec.constructor;

import java.io.IOException;
import java.util.function.Function;

import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.util.codec.CodecContext;

public class FieldKeyRegistryConstructor<T, K> extends AbstractRegistryConstructor<T, K> {

	public FieldKeyRegistryConstructor(RegistryKey<K> registry, Function<K, T> constructor) {
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
	
	@Override
	public boolean requiredField() {
		return true;
	}

}
