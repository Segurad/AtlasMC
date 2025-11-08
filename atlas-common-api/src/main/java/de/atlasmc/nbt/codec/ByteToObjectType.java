package de.atlasmc.nbt.codec;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;

final class ByteToObjectType<V> implements NBTCodec<V> {

	private final Class<?> clazz;
	private final IntFunction<V> toObject;
	private final ToIntFunction<V> toByte;
	
	public ByteToObjectType(Class<V> clazz, IntFunction<V> toObject, ToIntFunction<V> toByte) {
		this.clazz = Objects.requireNonNull(clazz);
		this.toObject = Objects.requireNonNull(toObject);
		this.toByte = Objects.requireNonNull(toByte);
	}
	
	@Override
	public Class<?> getType() {
		return clazz;
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
	public List<TagType> getTags() {
		return CodecTags.BYTE;
	}

}
