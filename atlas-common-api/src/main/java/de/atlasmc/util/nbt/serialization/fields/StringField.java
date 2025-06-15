package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class StringField<T> extends AbstractObjectField<T, String> {
	
	public StringField(CharSequence key, Function<T, String> supplier, BiConsumer<T, String> consumer) {
		super(key, TagType.STRING, supplier, consumer);
	}

	@Override
	public void serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		writer.writeStringTag(key, supplier.apply(value));
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		consumer.accept(value, reader.readStringTag());
	}

}
