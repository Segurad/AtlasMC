package de.atlasmc.nbt.tag;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;

final class RawFieldNBTCodec implements NBTCodec<NBT> {
	
	private final List<TagType> types;
	
	public RawFieldNBTCodec(List<TagType> types) {
		this.types = Objects.requireNonNull(types);
	}

	@Override
	public boolean serialize(CharSequence key, NBT value, NBTWriter writer, CodecContext context) throws IOException {
		writer.writeNBT(key, value);
		return true;
	}

	@Override
	public NBT deserialize(NBT value, NBTReader reader, CodecContext context) throws IOException {
		return reader.readNBT();
	}
	
	@Override
	public Class<?> getType() {
		return NBT.class;
	}

	@Override
	public List<TagType> getTags() {
		return types;
	}

}
