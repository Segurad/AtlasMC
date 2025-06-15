package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializable;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public class CompoundTypeField<T, K extends NBTSerializable> extends AbstractObjectField<T, K> {
	
	private final NBTSerializationHandler<K> handler;
	
	public CompoundTypeField(CharSequence key, Function<T, K> supplier, BiConsumer<T, K> consumer, NBTSerializationHandler<K> handler) {
		super(key, TagType.COMPOUND, supplier, consumer);
		this.handler = handler;
	}

	@Override
	public void serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		K v = supplier.apply(value);
		writer.writeCompoundTag(key);
		handler.serialize(v, writer, context);
		writer.writeEndTag();
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		K v = handler.deserialize(reader);
		consumer.accept(value, v);
	}

}
