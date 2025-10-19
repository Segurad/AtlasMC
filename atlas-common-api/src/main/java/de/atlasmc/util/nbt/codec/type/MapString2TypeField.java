package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.codec.NBTCodec;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class MapString2TypeField<V> extends ObjectType<Map<String, V>> {

	private final NBTCodec<V> codec;
	
	public MapString2TypeField(NBTCodec<V> codec) {
		this.codec = Objects.requireNonNull(codec);
	}

	@Override
	public boolean serialize(CharSequence key, Map<String, V> value, NBTWriter writer, CodecContext context) throws IOException {
		writer.writeCompoundTag(key);
		for (Entry<String, V> entry : value.entrySet()) {
			writer.writeCompoundTag(entry.getKey());
			codec.serialize(entry.getValue(), writer, context);
			writer.writeEndTag();
		}
		writer.writeEndTag();
		return true;
	}

	@Override
	public Map<String, V> deserialize(Map<String, V> value, NBTReader reader, CodecContext context) throws IOException {
		reader.readNextEntry();
		while (reader.getType() != TagType.TAG_END) {
			String key = reader.getFieldName().toString();
			V entry = codec.deserialize(reader);
			value.put(key, entry);
		}
		reader.readNextEntry();
		return value;
	}

	@Override
	public List<TagType> getTypes() {
		return COMPOUND;
	}

}
