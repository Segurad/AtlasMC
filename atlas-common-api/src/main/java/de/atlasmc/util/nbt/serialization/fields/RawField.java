package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;
import de.atlasmc.util.nbt.tag.NBT;

public class RawField<T> extends AbstractObjectField<T, NBT> {

	private final boolean includeKey;
	
	public RawField(CharSequence key, TagType type, Function<T, NBT> get, BiConsumer<T, NBT> set, boolean includeKey) {
		super(key, type, get, set, true);
		this.includeKey = includeKey;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		NBT v = get.apply(value);
		if (v == null)
			return true;
		writer.writeNBT(key, v);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		NBT nbt = reader.readNBT();
		if (!includeKey)
			nbt.setName(null);
		set.accept(value, nbt);
	}

}
