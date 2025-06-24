package de.atlasmc.util.nbt.serialization.fields;

import java.io.IOException;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.serialization.NBTSerializationContext;

public class StringToObjectField<T, K> extends AbstractObjectField<T, K> {

	private final Function<String, K> stringToObject;
	private final Function<K, String> objectToString;
	
	public StringToObjectField(CharSequence key, Function<T, K> get, BiConsumer<T, K> set, Function<String, K> stringToObject, Function<K, String> objectToString) {
		super(key, TagType.STRING, get, set, true);
		this.stringToObject = stringToObject;
		this.objectToString = objectToString;
	}

	@Override
	public boolean serialize(T value, NBTWriter writer, NBTSerializationContext context) throws IOException {
		K v = get.apply(value);
		if (v == null)
			return true;
		String raw = objectToString.apply(v);
		if (raw == null)
			return false;
		writer.writeStringTag(key, raw);
		return true;
	}

	@Override
	public void deserialize(T value, NBTReader reader, NBTSerializationContext context) throws IOException {
		String raw = reader.readStringTag();
		K v = stringToObject.apply(raw);
		set.accept(value, v);
	}

}
