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
	
	public NamespacedKeyField(CharSequence key, Function<T, NamespacedKey> supplier, BiConsumer<T, NamespacedKey> consumer) {
		super(key, TagType.STRING, supplier, consumer, true);
	}

	@Override
	public void serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		NamespacedKey key = supplier.apply(value);
		if (key == null)
			return;
		writer.writeStringTag(this.key, key.toString());
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		consumer.accept(value, NamespacedKey.of(reader.readStringTag()));
	}

}
