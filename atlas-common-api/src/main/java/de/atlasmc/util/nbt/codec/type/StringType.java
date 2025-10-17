package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;

import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

@Singleton
public class StringType extends ObjectType<String> {

	private static final StringType INSTANCE = new StringType();
	
	public static StringType getInstance() {
		return INSTANCE;
	}
	
	private StringType() {
		// singleton
	}
	
	@Override
	public boolean serialize(CharSequence key, String value, NBTWriter writer, CodecContext context) throws IOException {
		writer.writeStringTag(key, value);
		return true;
	}

	@Override
	public String deserialize(String value, NBTReader reader, CodecContext context) throws IOException {
		return reader.readStringTag();
	}

	@Override
	public List<TagType> getTypes() {
		return STRING;
	}

}
