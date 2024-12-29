package de.atlasmc.sound;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public interface Sound extends Namespaced {

	final long DEFAULT_SEED = 0x4aa2903b89ec2ff6L;

	/**
	 * Returns the fixed range value if the value is NaN no fixed range is set.
	 * @return fixed range
	 */
	float getFixedRange();
	
	/**
	 * Returns whether or not a fixed range is set.
	 * @return true if fixed range
	 * @implNote {@link #getFixedRange()} != {@link #getFixedRange()}
	 */
	boolean hasFixedRange();

	static Sound fromNBT(NBTReader reader) throws IOException {
		final TagType type = reader.getType();
		if (type == TagType.STRING) {
			return EnumSound.getByName(reader.readStringTag());
		} else if (type == TagType.COMPOUND) {
			NamespacedKey soundID = null;
			float range = Float.NaN;
			reader.readNextEntry();
			while (reader.getType() != TagType.TAG_END) {
				CharSequence key = reader.getFieldName();
				if (ResourceSound.NBT_SOUND_ID.equals(key)) {
					soundID = reader.readNamespacedKey();
				} else if (ResourceSound.NBT_RANGE.equals(key)) {
					range = reader.readFloatTag();
				} else {
					reader.skipTag();
				}
			}
			reader.readNextEntry();
			return new ResourceSound(soundID, range);
		} else {
			reader.skipTag();
		}
		return null;
	}
	
	static void toNBT(CharSequence key, Sound sound, NBTWriter writer, boolean systemData) throws IOException {
		if (sound instanceof EnumSound enumSound) {
			writer.writeStringTag(key, enumSound.getName());
		} else if (sound instanceof ResourceSound resource) {
			writer.writeCompoundTag(key);
			writer.writeNamespacedKey(ResourceSound.NBT_SOUND_ID, resource.getNamespacedKey());
			if (resource.hasFixedRange()) {
				writer.writeFloatTag(ResourceSound.NBT_RANGE, resource.getFixedRange());
			}
			writer.writeEndTag();
		}
	}
	
}
