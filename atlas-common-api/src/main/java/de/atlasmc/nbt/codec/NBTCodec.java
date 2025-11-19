package de.atlasmc.nbt.codec;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ToIntFunction;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.codec.Codec;
import de.atlasmc.util.codec.CodecContext;

public interface NBTCodec<T> extends Codec<T, NBTReader, NBTWriter, CodecContext> {
	
	default boolean serialize(T value, @NotNull NBTWriter output, CodecContext context) throws IOException {
		return serialize(null, value, output, context);
	}
	
	/**
	 * Serializes the given values to the given output as field.
	 * @param key to use
	 * @param value to serialize
	 * @param output the output
	 * @param context a arbitrary context for serialization
	 * @return true if success
	 * @throws IOException
	 */
	boolean serialize(CharSequence key, T value, @NotNull NBTWriter output, CodecContext context) throws IOException;
	
	/**
	 * Serializes the given values to the given output as partial field.
	 * If {@link #isField()} a error is thrown.
	 * @param value to serialize
	 * @param output the output
	 * @param context a arbitrary context for serialization
	 * @return true if success
	 * @throws IOException
	 */
	default boolean serializePartial(T value, @NotNull NBTWriter output, CodecContext context) throws IOException {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Deserialize the the input to a object as partial field.
	 * If {@link #isField()} a error is thrown.
	 * @param value the object deserialized to
	 * @param input the input
	 * @param context a arbitrary context for serialization
	 * @return deserialized object
	 * @throws IOException
	 */
	T deserialize(T value, @NotNull NBTReader input, CodecContext context) throws IOException;
	default T deserializePartial(T value, NBTReader input, CodecContext context) throws IOException {
		throw new UnsupportedOperationException();
	}
	
	/**
	 * Whether or not this is a representation of a field or partial component.
	 * If a codec is a partial component it may only return {@link TagType#COMPOUND} with {@link #getTags()}
	 * @return true if field
	 */
	default boolean isField() {
		return true;
	}
	
	/**
	 * Returns a immutable list of all tags that the type may be represented as
	 * @return tags
	 */
	List<TagType> getTags();
	
	/**
	 * Whether or not a given value may be used for deserialization
	 * @return
	 */
	default boolean isReuseValue() {
		return false;
	}
	
	@Override
	default CodecContext getDefaultContext() {
		return CodecContext.DEFAULT_SERVER;
	}
	
	public static <T> NBTCodecBuilder<T> builder(Class<T> clazz) {
		return new NBTCodecBuilder<>(clazz);
	}
	
	public static <T> NBTCodec<T> codecOrElse(Class<T> clazz, NBTCodec<? extends T> a, NBTCodec<? extends T> b) {
		return new OrElseCodec<>(clazz, a, b);
	}
	
	public static <T> NBTCodec<T> byteToObject(Class<T> clazz, IntFunction<T> toObject, ToIntFunction<T> toByte) {
		return new ByteToObjectCodec<>(clazz, toObject, toByte);
	}
	
	public static <T> NBTCodec<T> stringToObject(Class<T> clazz, Function<String, T> toObject, Function<T, String> toString) {
		return new StringToObjectCodec<>(clazz, toObject, toString);
	}

}
