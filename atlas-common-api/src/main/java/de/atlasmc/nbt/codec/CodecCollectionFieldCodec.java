package de.atlasmc.nbt.codec;

import java.io.IOException;
import java.util.List;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;

/**
 * Field codec that represents a value as a collection handled by a collection codec.
 * Works like {@link de.atlasmc.nbt.codec.field.CodecCollectionField} but as standalone field codec (e.g. for collection based item components).
 * The collection returned by the getter is passed to the collection codec for deserialization and must therefore be present and modifiable.
 * @param <T> type of the value
 * @param <C> type of the collection
 */
public class CodecCollectionFieldCodec<T, C> extends AbstractCollectionFieldCodec<T, C, NBTCodec<C>> {

	public CodecCollectionFieldCodec(CodecCollectionFieldCodecBuilder<T, C> builder) {
		super(builder);
	}

	@Override
	public Class<?> getType() {
		return clazz;
	}

	@Override
	public boolean serialize(CharSequence key, T type, NBTWriter writer, CodecContext context) throws IOException {
		if (!hasData.applyAsBoolean(type))
			return true;
		C value = getter.apply(type);
		return fieldType.serialize(key, value, writer, context);
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
		fieldType.deserialize(getter.apply(value), reader, context);
		return value;
	}

	@Override
	public List<TagType> getTags() {
		return fieldType.getTags();
	}

	@Override
	public boolean isReuseValue() {
		return true;
	}
	
	public static <T, V> CodecCollectionFieldCodecBuilder<T, V> builder(Class<T> clazz, NBTCodec<V> fieldCodec) {
		return new CodecCollectionFieldCodecBuilder<T, V>(clazz)
				.setFieldType(fieldCodec);
	}
	

}
