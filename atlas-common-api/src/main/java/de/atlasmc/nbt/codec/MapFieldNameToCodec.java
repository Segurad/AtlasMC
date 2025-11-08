package de.atlasmc.nbt.codec;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;

/**
 * NBTCodec for fields of {@link TagType#COMPOUND} that map object to the fields name.
 * @param <V>
 */
final class MapFieldNameToCodec<V> implements NBTCodec<Map<String, V>> {

	private final NBTCodec<V> codec;
	
	public MapFieldNameToCodec(NBTCodec<V> codec) {
		this.codec = Objects.requireNonNull(codec);
	}
	
	@Override
	public Class<?> getType() {
		return Map.class;
	}

	@Override
	public boolean serialize(CharSequence key, Map<String, V> value, NBTWriter writer, CodecContext context) throws IOException {
		writer.writeCompoundTag(key);
		if (codec.isField()) {
			for (Entry<String, V> entry : value.entrySet()) {
				codec.serialize(entry.getKey(), entry.getValue(), writer, context);
			}
		} else {
			for (Entry<String, V> entry : value.entrySet()) {
				writer.writeCompoundTag(entry.getKey());
				codec.serialize(entry.getValue(), writer, context);
				writer.writeEndTag();
			}
		}
		writer.writeEndTag();
		return true;
	}

	@Override
	public Map<String, V> deserialize(Map<String, V> value, NBTReader reader, CodecContext context) throws IOException {
		reader.readNextEntry();
		if (codec.isField()) {
			while (reader.getType() != TagType.TAG_END) {
				String key = reader.getFieldName().toString();
				V entry = codec.deserialize(reader);
				value.put(key, entry);
			}
		} else {
			while (reader.getType() != TagType.TAG_END) {
				String key = reader.getFieldName().toString();
				reader.readNextEntry();
				V entry = codec.deserialize(reader);
				value.put(key, entry);
			}
		}
		reader.readNextEntry();
		return value;
	}

	@Override
	public List<TagType> getTags() {
		return CodecTags.COMPOUND;
	}

}
