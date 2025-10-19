package de.atlasmc.util.nbt.codec.type;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.codec.NBTCodec;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class MapType2Type<K, V> extends ObjectType<Map<K, V>> {

	private final NBTCodec<V> handler;
	private final Function<V, K> keySupplier;
	
	public MapType2Type(NBTCodec<V> codec, Function<V, K> keySupplier) {
		this.handler = Objects.requireNonNull(codec);
		this.keySupplier = Objects.requireNonNull(keySupplier);
	}

	@Override
	public boolean serialize(CharSequence key, Map<K, V> value, NBTWriter writer, CodecContext context) throws IOException {
		writer.writeCompoundTag(key);
		for (V entry : value.values()) {
			handler.serialize(entry, writer, context);
		}
		writer.writeEndTag();
		return true;
	}

	@Override
	public Map<K, V> deserialize(Map<K, V> value, NBTReader reader, CodecContext context) throws IOException {
		reader.readNextEntry();
		while (reader.getType() != TagType.TAG_END) {
			V entry = handler.deserialize(reader);
			value.put(keySupplier.apply(entry), entry);
		}
		reader.readNextEntry();
		return value;
	}

	@Override
	public List<TagType> getTypes() {
		return COMPOUND;
	}

}
