package de.atlasmc.nbt.codec;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.map.key.CharKey;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

/**
 * Codec that represents a index to value map as list of compounds.
 * Each compound contains the index as int field and the value as field serialized by the given codec.
 * The given list must not container null values.
 * <pre>
 * [{indexKey: 0, valueKey: {...}}, {indexKey: 1, valueKey: {...}}]
 * </pre>
 * The fields within a compound may appear in any order. Unknown fields are skipped.
 * @param <T> type of the values
 */
public class MapSearchedIndexToCodec<T> implements NBTCodec<Int2ObjectMap<T>> {

	private final CharKey indexKey;
	private final CharKey valueKey;
	private final NBTCodec<T> valueCodec;

	/**
	 * Creates a new codec
	 * @param indexKey the key of the index field
	 * @param valueKey the key of the value field
	 * @param valueCodec the codec used for the value field
	 */
	public MapSearchedIndexToCodec(CharSequence indexKey, CharSequence valueKey, NBTCodec<T> valueCodec) {
		this.indexKey = CharKey.literal(Objects.requireNonNull(indexKey, "indexKey"));
		this.valueKey = CharKey.literal(Objects.requireNonNull(valueKey, "valueKey"));
		this.valueCodec = Objects.requireNonNull(valueCodec, "valueCodec");
		if (this.indexKey.equals(this.valueKey))
			throw new IllegalArgumentException("Index key and value key can not be equal: " + indexKey);
	}

	@Override
	public Class<?> getType() {
		return Int2ObjectMap.class;
	}

	@Override
	public boolean serialize(CharSequence key, Int2ObjectMap<T> value, NBTWriter writer, CodecContext context) throws IOException {
		int size = 0;
		for (T v : value.values()) {
			if (v != null)
				size++;
		}
		writer.writeListTag(key, TagType.COMPOUND, size);
		if (size == 0)
			return true;
		for (Entry<T> entry : value.int2ObjectEntrySet()) {
			T v = entry.getValue();
			if (v == null)
				continue;
			writer.writeCompoundTag();
			writer.writeIntTag(indexKey, entry.getIntKey());
			valueCodec.serialize(valueKey, v, writer, context);
			writer.writeEndTag();
		}
		return true;
	}

	@Override
	public Int2ObjectMap<T> deserialize(Int2ObjectMap<T> value, NBTReader reader, CodecContext context) throws IOException {
		final Int2ObjectMap<T> map = value != null ? value : new Int2ObjectOpenHashMap<>();
		final TagType listType = reader.getListType();
		if (listType == TagType.TAG_END || reader.getNextPayload() == 0) {
			reader.readNextEntry();
			reader.readNextEntry();
			return map;
		}
		if (listType != TagType.COMPOUND)
			throw new NBTException("Expected list of type COMPOUND but was: " + listType);
		reader.readNextEntry(); // enter list
		while (reader.getRestPayload() > 0) {
			reader.readNextEntry(); // enter element compound
			boolean hasIndex = false;
			int index = -1;
			T v = null;
			while (reader.getType() != TagType.TAG_END) {
				final CharKey fieldName = reader.getFieldName();
				final TagType type = reader.getType();
				if (indexKey.equals(fieldName) && type == TagType.INT) {
					index = reader.readIntTag();
					hasIndex = true;
				} else if (valueKey.equals(fieldName) && valueCodec.getTags().contains(type)) {
					v = valueCodec.deserialize(null, reader, context);
				} else {
					reader.skipTag();
				}
			}
			reader.readNextEntry(); // exit element compound
			if (!hasIndex)
				throw new NBTException("Missing index field: " + indexKey);
			if (v == null)
				throw new NBTException("Missing value field: " + valueKey + " for index: " + index);
			map.put(index, v);
		}
		reader.readNextEntry(); // exit list
		return map;
	}

	@Override
	public List<TagType> getTags() {
		return CodecTags.LIST;
	}

	@Override
	public boolean isReuseValue() {
		return true;
	}

}
