package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;

import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

@Singleton
public class ByteArrayType extends ObjectType<byte[]> {

	private static final ByteArrayType INSTANCE = new ByteArrayType();
	
	public static ByteArrayType getInstance() {
		return INSTANCE;
	}
	
	private ByteArrayType() {
		// singleton
	}
	
	@Override
	public boolean serialize(CharSequence key, byte[] value, NBTWriter writer, CodecContext context) throws IOException {
		if (value.length == 0)
			return true;
		writer.writeByteArrayTag(key, value);
		return true;
	}

	@Override
	public byte[] deserialize(byte[] value, NBTReader reader, CodecContext context) throws IOException {
		return reader.readByteArrayTag();
	}

	@Override
	public List<TagType> getTypes() {
		return BYTE_ARRAY;
	}

}
