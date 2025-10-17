package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

@Singleton
public class UUIDType extends ObjectType<UUID> {

	private static final UUIDType INSTANCE = new UUIDType();
	
	public static UUIDType getInstance() {
		return INSTANCE;
	}
	
	private UUIDType() {
		// singleton
	}
	
	@Override
	public boolean serialize(CharSequence key, UUID value, NBTWriter writer, CodecContext context) throws IOException {
		writer.writeUUID(key, value);
		return true;
	}

	@Override
	public UUID deserialize(UUID value, NBTReader reader, CodecContext context) throws IOException {
		switch (reader.getType()) {
		case INT_ARRAY:
			return reader.readUUID();
		case STRING:
			return UUID.fromString(reader.readStringTag());
		// list of int will be ignored
		default:
			throw new NBTException("Unexpected tag type: " + reader.getType());
		}
	}

	@Override
	public List<TagType> getTypes() {
		return INT_ARRAY_STRING;
	}

}
