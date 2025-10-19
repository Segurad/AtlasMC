package de.atlasmc.node.sound;

import java.io.IOException;
import java.util.List;
import de.atlasmc.util.EnumUtil;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.codec.type.ObjectType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

final class SoundType extends ObjectType<Sound> {

	private static final List<TagType> TYPES = List.of(TagType.STRING, TagType.COMPOUND);

	private static final SoundType INSTANCE = new SoundType();
	
	public static SoundType getInstance() {
		return INSTANCE;
	}
	
	private SoundType() {
		// singleton
	}
	
	@Override
	public boolean serialize(CharSequence key, Sound value, NBTWriter writer, CodecContext context) throws IOException {
		if (value instanceof EnumSound enumSound) {
			writer.writeStringTag(key, enumSound.getName());
		} else if (value instanceof ResourceSound resource) {
			writer.writeCompoundTag(key);
			ResourceSound.NBT_HANDLER.serialize(resource, writer, context);
			writer.writeEndTag();
		} else {
			return false;
		}
		return true;
	}

	@Override
	public Sound deserialize(Sound value, NBTReader reader, CodecContext context) throws IOException {
		final TagType type = reader.getType();
		Sound sound;
		switch (type) {
		case STRING:
			sound = EnumUtil.getByName(EnumSound.class, reader.readStringTag());
			break;
		case COMPOUND:
			reader.readNextEntry();
			sound = ResourceSound.NBT_HANDLER.deserialize(reader, context);
			break;
		default:
			throw new NBTException("Unexpected type: " + type);
		}
		return sound;
	}

	@Override
	public List<TagType> getTypes() {
		return TYPES;
	}

}
