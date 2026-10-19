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

final class EnumStringNBTCodec<E extends Enum<?> & EnumName> implements NBTCodec<E> {

	private final Class<E> clazz;
	
	public EnumStringNBTCodec(Class<E> clazz) {
		this.clazz = Objects.requireNonNull(clazz, "class");
	}

	@Override
	public boolean serialize(CharSequence key, E value, NBTWriter writer, CodecContext context) throws IOException {
		if (!clazz.isInstance(value))
			return false;
		writer.writeStringTag(key, value.getName());
		return true;
	}

	@Override
	public E deserialize(E value, NBTReader reader, CodecContext context) throws IOException {
		return EnumUtil.getByName(clazz, reader.readStringTag());
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
