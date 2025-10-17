package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class StringToObjectType<V> extends ObjectType<V> {

	private final Function<String, V> stringToObject;
	private final Function<V, String> objectToString;
	
	public StringToObjectType(Function<String, V> stringToObject, Function<V, String> objectToString) {
		this.stringToObject = Objects.requireNonNull(stringToObject);
		this.objectToString = Objects.requireNonNull(objectToString);
	}

	@Override
	public boolean serialize(CharSequence key, V value, NBTWriter writer, CodecContext context) throws IOException {
		String raw = objectToString.apply(value);
		if (raw == null)
			return false;
		writer.writeStringTag(key, raw);
		return true;
	}

	@Override
	public V deserialize(V value, NBTReader reader, CodecContext context) throws IOException {
		String raw = reader.readStringTag();
		return stringToObject.apply(raw);
	}

	@Override
	public List<TagType> getTypes() {
		return STRING;
	}

}
