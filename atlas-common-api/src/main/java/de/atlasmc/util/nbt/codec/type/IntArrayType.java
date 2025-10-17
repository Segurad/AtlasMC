package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;

import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

@Singleton
public class IntArrayType extends ObjectType<int[]> {

	private static final IntArrayType INSTANCE = new IntArrayType();
	
	public static IntArrayType getInstance() {
		return INSTANCE;
	}
	
	private IntArrayType() {
		// singleton
	}
	
	@Override
	public boolean serialize(CharSequence key, int[] value, NBTWriter writer, CodecContext context) throws IOException {
		writer.writeIntArrayTag(key, value);
		return true;
	}

	@Override
	public int[] deserialize(int[] value, NBTReader reader, CodecContext context) throws IOException {
		return reader.readIntArrayTag();
	}

	@Override
	public List<TagType> getTypes() {
		return INT_ARRAY;
	}

}
