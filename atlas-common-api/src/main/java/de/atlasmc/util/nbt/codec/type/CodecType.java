package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.codec.NBTCodec;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CodecType<V> extends ObjectType<V> {
	
	private final NBTCodec<V> codec;
	
	@SuppressWarnings("unchecked")
	public CodecType(NBTCodec<? extends V> codec) {
		this.codec = (NBTCodec<V>) Objects.requireNonNull(codec);
	}

	@Override
	public boolean serialize(CharSequence key, V value, NBTWriter writer, CodecContext context) throws IOException {
		if (!codec.getType().isInstance(value))
			return false;
		writer.writeCompoundTag(key);
		codec.serialize(value, writer, context);
		writer.writeEndTag();
		return true;
	}

	@Override
	public V deserialize(V value, NBTReader reader, CodecContext context) throws IOException {
		reader.readNextEntry();
		return codec.deserialize(value, reader, context);
	}

	@Override
	public List<TagType> getTypes() {
		return COMPOUND;
	}

}
