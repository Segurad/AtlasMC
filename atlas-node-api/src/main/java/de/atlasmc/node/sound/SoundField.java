package de.atlasmc.node.sound;

import java.io.IOException;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.util.EnumUtil;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.codec.type.AbstractObjectField;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

final class SoundField<T> extends AbstractObjectField<T, Sound> {

	private static final List<TagType> TYPES = List.of(TagType.STRING, TagType.COMPOUND);
	
	private final Sound defaultSound;
	
	public SoundField(CharSequence key, Function<T, Sound> get, BiConsumer<T, Sound> set, Sound defaultSound) {
		super(key, TYPES, get, set, true);
		this.defaultSound = defaultSound;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, CodecContext context) throws IOException {
		Sound sound = get.apply(value);
		if (sound == null)
			return true;
		if (defaultSound != null && defaultSound.equals(sound))
			return true;
		if (sound instanceof EnumSound enumSound) {
			writer.writeStringTag(key, enumSound.getName());
		} else if (sound instanceof ResourceSound resource) {
			writer.writeCompoundTag(key);
			ResourceSound.NBT_HANDLER.serialize(resource, writer, context);
			writer.writeEndTag();
		} else {
			return false;
		}
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, CodecContext context) throws IOException {
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
		set.accept(value, sound);
	}

}
