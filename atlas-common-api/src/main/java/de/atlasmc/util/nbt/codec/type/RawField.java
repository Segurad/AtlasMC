package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.tag.NBT;

public class RawField<T> extends AbstractObjectField<T, NBT> {

	private final boolean includeKey;
	
	public RawField(CharSequence key, List<TagType> types, Function<T, NBT> get, BiConsumer<T, NBT> set, boolean includeKey) {
		super(key, types, get, set, true);
		this.includeKey = includeKey;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, CodecContext context) throws IOException {
		NBT v = get.apply(value);
		if (v == null)
			return true;
		writer.writeNBT(key, v);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, CodecContext context) throws IOException {
		NBT nbt = reader.readNBT();
		if (!includeKey)
			nbt.setName(null);
		set.accept(value, nbt);
	}

}
