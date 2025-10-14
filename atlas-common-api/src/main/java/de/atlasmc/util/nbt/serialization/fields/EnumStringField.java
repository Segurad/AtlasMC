package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumUtil;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class EnumStringField<T, K extends Enum<K> & EnumName> extends AbstractObjectField<T, K> {

	private final Class<K> clazz;
	private final K defaultValue;
	
	public EnumStringField(CharSequence key, Function<T, K> get, BiConsumer<T, K> set, Class<K> clazz, K defaultValue) {
		super(key, STRING, get, set, true);
		this.defaultValue = defaultValue;
		this.clazz = clazz;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		K v = get.apply(value);
		if (useDefault && v == defaultValue)
			return true;
		writer.writeStringTag(key, v.getName());
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		K v = EnumUtil.getByName(clazz, reader.readStringTag());
		set.accept(value, v);
	}

}
