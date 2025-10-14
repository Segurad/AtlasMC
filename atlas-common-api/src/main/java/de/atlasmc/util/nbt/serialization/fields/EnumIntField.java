package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.IDHolder;
import de.atlasmc.util.EnumUtil;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class EnumIntField<T, K extends Enum<K> & IDHolder> extends AbstractObjectField<T, K> {

	private final Class<K> clazz;
	private final K defaultValue;
	
	public EnumIntField(CharSequence key, Function<T, K> get, BiConsumer<T, K> set, Class<K> clazz, K defaultValue) {
		super(key, INT, get, set, true);
		this.clazz = clazz;
		this.defaultValue = defaultValue;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		K v = get.apply(value);
		if (useDefault && v == defaultValue)
			return true;
		writer.writeIntTag(key, v.getID());
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		K v = EnumUtil.getByID(clazz, reader.readIntTag());
		set.accept(value, v);
	}

}
