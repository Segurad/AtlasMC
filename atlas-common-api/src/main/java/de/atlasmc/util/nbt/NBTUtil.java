package de.atlasmc.util.nbt;

import java.io.IOException;

import de.atlasmc.util.nbt.io.NBTReader;

public class NBTUtil {
	
	protected NBTUtil() {}
	
	public static <H> void readNBT(NBTFieldContainer<H> container, H holder, NBTReader reader) throws IOException {
		readCompound(container, holder, reader, null);
	}

	
	public static <H> void readNBT(NBTFieldContainer<H> container, H holder, NBTReader reader, CustomTagContainer customTags) throws IOException {
		if (container == null)
			throw new IllegalArgumentException("Container can not be null!");
		if (holder == null)
			throw new IllegalArgumentException("Holder can not be null!");
		if (reader == null)
			throw new IllegalArgumentException("Reader can not be null!");
		readCompound(container, holder, reader, customTags);
	}

	private static <H> void readCompound(NBTFieldContainer<H> container, H holder, NBTReader reader, CustomTagContainer customTags) throws IOException {
		final int depth = reader.getDepth(); // set the root depth
		while (depth <= reader.getDepth()) { // ensure to stay over or at root depth
			TagType type = reader.getType();
			if (type == TagType.TAG_END) {
				reader.readNextEntry(); // return in parent compound
				continue; // go to next iteration for further reading or break if reached end
			}
			final CharSequence key = reader.getFieldName();
			if (type == TagType.COMPOUND) {
				NBTFieldContainer<H> childContainer = container.getContainer(key);
				if (childContainer != null) { // enter compound if not null
					reader.readNextEntry();
					readCompound(childContainer, holder, reader, customTags);
					continue;
				} // if compound is null try read as field
			}
			NBTField<H> field = container.getField(key);
			if (field != null) { // if field exists set it
				field.setField(holder, reader);
			} else if (container.hasUnknownFieldHandler()) { // if field does not exist try to use unknown field handler
				field = container.getUnknownFieldHandler();
				field.setField(holder, reader);
			} else if (customTags != null) { // if not handler is present try to write in custom tags
				customTags.addCustomTag(reader.readNBT());
			} else {
				reader.skipTag(); // fallback just skip
			}
		}
		if (reader.getType() == TagType.TAG_END) // escape from component
			reader.readNextEntry();
	}
	
}
