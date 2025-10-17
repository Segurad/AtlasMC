package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;

import de.atlasmc.util.annotation.Singleton;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import it.unimi.dsi.fastutil.ints.IntSet;

@Singleton
public class IntSetType extends ObjectType<IntSet> {
	
	private static final IntSetType INSTANCE = new IntSetType();
	
	public static IntSetType getInstance() {
		return INSTANCE;
	}
	
	private IntSetType() {
		// singleton
	}
	
	@Override
	public List<TagType> getTypes() {
		return INT_LIST;
	}

	@Override
	public boolean serialize(CharSequence key, IntSet value, NBTWriter writer, CodecContext context) throws IOException {
		int[] array = value.toIntArray();
		writer.writeIntArrayTag(key, array);
		return true;
	}

	@Override
	public IntSet deserialize(IntSet value, NBTReader reader, CodecContext context) throws IOException {
		int[] array = reader.readIntArrayTag();
		for (int i : array) {
			value.add(i);
		}
		return value;
	}

}
