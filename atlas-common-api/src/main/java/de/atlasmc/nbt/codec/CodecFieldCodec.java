package de.atlasmc.nbt.codec;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;

public class CodecFieldCodec<T, V> extends AbstractCodec<T> {

	private final NBTCodec<V> fieldCodec;
	private final Function<T, V> get;
	private final BiConsumer<T, V> set;
	
	public CodecFieldCodec(CodecFieldCodecBuilder<T, V> builder) {
		super(builder);
		this.get = Objects.requireNonNull(builder.getter(), "get");
		this.set = Objects.requireNonNull(builder.setter(), "set");
		this.fieldCodec = Objects.requireNonNull(builder.fieldCodec(), "fieldCodec");
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean serialize(CharSequence key, T value, NBTWriter output, CodecContext context) throws IOException {
		if (redirectAfterConstruction && value instanceof NBTSerializable s && s.getNBTCodec() != this)
		    return ((NBTCodec<T>) s.getNBTCodec()).serialize(key, value, output, context);
		var v = get.apply(value);
		return fieldCodec.serialize(key, v, output, context);
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
		var v = fieldCodec.deserialize(null, reader, context);
		set.accept(value, v);
		return value;
	}
	
	@Override
	public List<TagType> getTags() {
		return fieldCodec.getTags();
	}
	
	public static <T, V> CodecFieldCodecBuilder<T, V> builder(Class<T> clazz, NBTCodec<V> codec) {
		return new CodecFieldCodecBuilder<T, V>(clazz)
				.fieldCodec(codec);
	}

}
