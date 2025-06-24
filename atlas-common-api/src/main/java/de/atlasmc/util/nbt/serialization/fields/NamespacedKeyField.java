package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class NamespacedKeyField<T> extends AbstractObjectField<T, NamespacedKey> {
	
	public NamespacedKeyField(CharSequence key, Function<T, NamespacedKey> get, BiConsumer<T, NamespacedKey> set) {
		super(key, TagType.STRING, get, set, true);
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		NamespacedKey key = get.apply(value);
		if (key == null)
			return true;
		writer.writeStringTag(this.key, key.toString());
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		set.accept(value, NamespacedKey.of(reader.readStringTag()));
	}

}
