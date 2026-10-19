package de.atlasmc.nbt.codec;

import java.io.IOException;
import java.util.List;

import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;

/**
 * Field codec that represents a value as a list of elements of a specific codec.
 * Works like {@link de.atlasmc.nbt.codec.field.CodecListField} but as standalone field codec (e.g. for list based item components).
 * The supported Tags of the elements are defined by {@link NBTCodec#getTags()} of the element codec.
 * For serialization the list's type is determined by the first element within the tags.
 * The element codec is expected to serialize all elements as the same {@link TagType} and may support multiple read types.
 * @param <T> type of the value
 * @param <V> type of the elements
 */
public class CodecListFieldCodec<T, V> extends AbstractCollectionFieldCodec<T, List<V>, NBTCodec<V>> {

	private final boolean optional;
	
	public CodecListFieldCodec(CodecListFieldCodecBuilder<T, V> builder) {
		super(builder);
		this.optional = builder.optional();
	}

	@Override
	public Class<?> getType() {
		return clazz;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean serialize(CharSequence key, T value, NBTWriter output, CodecContext context) throws IOException {
		if (redirectAfterConstruction && value instanceof NBTSerializable s && s.getNBTCodec() != this)
			return ((NBTCodec<T>) s.getNBTCodec()).serialize(key, value, output, context);
		if (!hasData.applyAsBoolean(value)) {
			if (!optional)
				output.writeListTag(key, fieldType.getTags().get(0), 0);
			return true;
		}
		final List<V> list = getter.apply(value);
		final int size = list.size();
		if (size == 0) { // check again because getter may return empty list
			if (!optional)
				output.writeListTag(key, fieldType.getTags().get(0), 0);
			return true;
		}
		output.writeListTag(key, fieldType.getTags().get(0), size);
		for (int i = 0; i < size; i++) {
			fieldType.serialize(null, list.get(i), output, context);
		}
		return true;
	}

	@Override
	public T deserialize(T value, NBTReader reader, CodecContext context) throws IOException {
		if (value == null)
			value = construct(reader, context);
		// redirect if required for correct deserialization of child implementations
		if (redirectAfterConstruction && value instanceof NBTSerializable serializable) {
			@SuppressWarnings("unchecked")
			NBTCodec<T> handler = (NBTCodec<T>) serializable.getNBTCodec();
			if (handler != this) {
				return handler.deserialize(value, reader, context);
			}
		}
		final TagType listType = reader.getListType();
		if (listType == TagType.TAG_END || reader.getNextPayload() == 0) {
			reader.readNextEntry();
			reader.readNextEntry();
			return value;
		}
		if (!fieldType.getTags().contains(listType))
			throw new NBTException("Unexpected list type: " + listType + " expected one of " + fieldType.getTags());
		final List<V> list = getter.apply(value);
		reader.readNextEntry();
		while (reader.getRestPayload() > 0) {
			list.add(fieldType.deserialize(null, reader, context));
		}
		reader.readNextEntry();
		return value;
	}

	@Override
	public List<TagType> getTags() {
		return CodecTags.LIST;
	}
	
	public static <T, V> CodecListFieldCodecBuilder<T, V> builder(Class<T> clazz, NBTCodec<V> fieldType) {
		return new CodecListFieldCodecBuilder<T, V>(clazz)
				.setFieldType(fieldType);
	}

}
