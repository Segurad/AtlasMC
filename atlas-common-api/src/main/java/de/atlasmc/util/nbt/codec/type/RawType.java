package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.tag.NBT;

public class RawType extends ObjectType<NBT> {
	
	private final List<TagType> types;
	
	public RawType(List<TagType> types) {
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
	public List<TagType> getTypes() {
		return types;
	}

}
