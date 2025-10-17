package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;

import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

@Singleton
public class IntObjectType extends ObjectType<Integer> {

	private static final IntObjectType INSTANCE = new IntObjectType();
	
	public static IntObjectType getInstance() {
		return INSTANCE;
	}
	
	private IntObjectType() {
		// singleton
	}
	
	@Override
	public boolean serialize(CharSequence key, Integer value, NBTWriter writer, CodecContext context) throws IOException {
		writer.writeIntTag(key, value);
		return true;
	}

	@Override
	public Integer deserialize(Integer value, NBTReader reader, CodecContext context) throws IOException {
		return reader.readIntTag();
	}

	@Override
	public List<TagType> getTypes() {
		return INT;
	}

}
