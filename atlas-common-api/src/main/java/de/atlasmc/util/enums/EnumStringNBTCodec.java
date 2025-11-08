package de.atlasmc.util.enums;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.CodecTags;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;

final class EnumStringNBTCodec<V, E extends Enum<?> & EnumName> implements NBTCodec<V> {

	private final Class<E> clazz;
	
	public EnumStringNBTCodec(Class<E> clazz) {
		this.clazz = Objects.requireNonNull(clazz);
	}

	@Override
	public boolean serialize(CharSequence key, V value, NBTWriter writer, CodecContext context) throws IOException {
		if (!clazz.isInstance(value))
			return false;
		@SuppressWarnings("unchecked")
		E enumValue = (E) value;
		writer.writeStringTag(key, enumValue.getName());
		return true;
	}

	@Override
	public V deserialize(V value, NBTReader reader, CodecContext context) throws IOException {
		@SuppressWarnings("unchecked")
		V v = (V) EnumUtil.getByName(clazz, reader.readStringTag());
		return v;
	}
	
	@Override
	public Class<?> getType() {
		return clazz;
	}

	@Override
	public List<TagType> getTags() {
		return CodecTags.STRING;
	}

}
