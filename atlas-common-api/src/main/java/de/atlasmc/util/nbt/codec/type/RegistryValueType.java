package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class RegistryValueType<V extends Namespaced> extends ObjectType<V> {

	private final RegistryKey<V> registry;
	
	public RegistryValueType(RegistryKey<V> registry) {
		this.registry = Objects.requireNonNull(registry);
	}

	@Override
	public boolean serialize(CharSequence key, V value, NBTWriter writer, CodecContext context) throws IOException {
		if (!registry.get().containsKey(value.getNamespacedKey()))
			return false;
		writer.writeNamespacedKey(key, context.clientSide ? value.getClientKey() : value.getNamespacedKey());
		return true;
	}

	@Override
	public V deserialize(V value, NBTReader reader, CodecContext context) throws IOException {
		return registry.getValue(reader.readStringTag());
	}

	@Override
	public List<TagType> getTypes() {
		return STRING;
	}

}
