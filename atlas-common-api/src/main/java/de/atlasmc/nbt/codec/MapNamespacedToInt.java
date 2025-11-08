package de.atlasmc.nbt.codec;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;

final class MapNamespacedToInt<K extends Namespaced> implements NBTCodec<Object2IntMap<K>> {

	private final Function<String, K> keySupplier;
	
	public MapNamespacedToInt(Function<String, K> keySupplier) {
		this.keySupplier = Objects.requireNonNull(keySupplier);
	}
	
	@Override
	public Class<?> getType() {
		return Object2IntMap.class;
	}

	@Override
	public boolean serialize(CharSequence key, Object2IntMap<K> value, NBTWriter writer, CodecContext context) throws IOException {
		writer.writeCompoundTag(key);
		for (Entry<K> entry : value.object2IntEntrySet()) {
			K k = entry.getKey(); 
			NamespacedKey nkey = context.clientSide ? k.getClientKey() : k.getNamespacedKey();
			writer.writeIntTag(nkey.toString(), entry.getIntValue());
		}
		writer.writeEndTag();
		return true;
	}

	@Override
	public Object2IntMap<K> deserialize(Object2IntMap<K> value, NBTReader reader, CodecContext context) throws IOException {
		reader.readNextEntry();
		while (reader.getType() != TagType.TAG_END) {
			value.put(keySupplier.apply(reader.getFieldName().toString()), reader.readIntTag());
		}
		reader.readNextEntry();
		return value;
	}

	@Override
	public List<TagType> getTags() {
		return CodecTags.COMPOUND;
	}

}
