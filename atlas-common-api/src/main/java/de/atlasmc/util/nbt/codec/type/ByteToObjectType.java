package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class ByteToObjectType<V> extends ObjectType<V> {

	private final IntFunction<V> toObject;
	private final ToIntFunction<V> toByte;
	
	public ByteToObjectType(IntFunction<V> toObject, ToIntFunction<V> toByte) {
		this.toObject = Objects.requireNonNull(toObject);
		this.toByte = Objects.requireNonNull(toByte);
	}
	
	@Override
	public boolean serialize(CharSequence key, V value, NBTWriter writer, CodecContext context) throws IOException {
		writer.writeByteTag(key, toByte.applyAsInt(value));
		return true;
	}

	@Override
	public V deserialize(V value, NBTReader reader, CodecContext context) throws IOException {
		return toObject.apply(reader.readByteTag() & 0xFF);
	}

	@Override
	public List<TagType> getTypes() {
		return BYTE;
	}

}
