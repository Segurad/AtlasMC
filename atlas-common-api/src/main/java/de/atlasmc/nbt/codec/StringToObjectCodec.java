package de.atlasmc.nbt.codec;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;

final class StringToObjectCodec<V> implements NBTCodec<V> {

	private final Class<V> clazz;
	private final Function<String, V> stringToObject;
	private final Function<V, String> objectToString;
	
	public StringToObjectCodec(Class<V> clazz, Function<String, V> stringToObject, Function<V, String> objectToString) {
		this.clazz = Objects.requireNonNull(clazz);
		this.stringToObject = Objects.requireNonNull(stringToObject);
		this.objectToString = Objects.requireNonNull(objectToString);
	}
	
	@Override
	public Class<?> getType() {
		return clazz;
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
	public List<TagType> getTags() {
		return CodecTags.STRING;
	}

}
