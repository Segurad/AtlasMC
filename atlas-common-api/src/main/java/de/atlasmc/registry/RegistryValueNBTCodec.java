package de.atlasmc.registry;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.CodecTags;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;

final class RegistryValueNBTCodec<V extends Namespaced> implements NBTCodec<V> {

	private final RegistryKey<V> registry;
	
	public RegistryValueNBTCodec(RegistryKey<V> registry) {
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
	public List<TagType> getTags() {
		return CodecTags.STRING;
	}

	@Override
	public Class<?> getType() {
		return registry.get().getClass();
	}

}
