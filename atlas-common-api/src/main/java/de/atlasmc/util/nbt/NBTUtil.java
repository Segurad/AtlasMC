package de.atlasmc.util.nbt;

import java.io.IOException;

import de.atlasmc.util.nbt.io.NBTReader;

public class NBTUtil {
	
	protected NBTUtil() {}

	
	public static <H> void readNBT(NBTFieldSet<H> set, H holder, NBTReader reader) throws IOException {
		if (set == null)
			throw new IllegalArgumentException("Set can not be null!");
		if (holder == null)
			throw new IllegalArgumentException("Holder can not be null!");
		if (reader == null)
			throw new IllegalArgumentException("Reader can not be null!");
		readCompound(set, holder, reader);
	}

	private static <H> void readCompound(NBTFieldSet<H> set, H holder, NBTReader reader) throws IOException {
		final int depth = reader.getDepth(); // set the root depth
		while (depth <= reader.getDepth()) { // ensure to stay over or at root depth
			TagType type = reader.getType();
			if (type == TagType.TAG_END) {
				reader.readNextEntry(); // return in parent compound
				continue; // go to next iteration for further reading or break if reached end
			}
			final CharSequence key = reader.getFieldName();
			if (type == TagType.COMPOUND) {
				NBTFieldSet<H> childContainer = set.getSet(key);
				if (childContainer != null) { // enter compound if not null
					reader.readNextEntry();
					readCompound(childContainer, holder, reader);
					continue;
				} // if compound is null try read as field
			}
			NBTField<H> field = set.getField(key);
			if (field != null) { // if field exists set it
				field.setField(holder, reader);
			} else if (set.hasUnknownFieldHandler()) { // if field does not exist try to use unknown field handler
				field = set.getUnknownFieldHandler();
				field.setField(holder, reader);
			} else {
				reader.skipTag(); // fallback just skip
			}
		}
		if (reader.getType() == TagType.TAG_END) // escape from component
			reader.readNextEntry();
	}
	
}
