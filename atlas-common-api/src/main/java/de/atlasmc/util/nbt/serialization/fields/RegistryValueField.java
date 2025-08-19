package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class RegistryValueField<T, K extends Namespaced> extends AbstractObjectField<T, K> {

	private final RegistryKey<K> registry;
	
	public RegistryValueField(CharSequence key, Function<T, K> get, BiConsumer<T, K> set, RegistryKey<K> registry) {
		super(key, STRING, get, set, true);
		this.registry = registry;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		K v = get.apply(value);
		if (v == null)
			return true;
		if (!registry.get().containsKey(v.getNamespacedKey()))
			return false;
		writer.writeNamespacedKey(key, context.clientSide ? v.getClientKey() : v.getNamespacedKey());
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		NamespacedKey key = reader.readNamespacedKey();
		K v = registry.getValue(key);
		set.accept(value, v);
	}

}
