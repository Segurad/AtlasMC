package de.atlasmc.nbt.codec;

import java.io.IOException;
import java.util.List;
import java.util.function.Supplier;

import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.constructor.Constructor;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.nbt.io.NBTWriter;
import de.atlasmc.util.codec.CodecContext;

/**
 * Field codec that only constructs a value and redirects to the codec returned by {@link NBTSerializable#getNBTCodec()} of the constructed value.
 * Used as base codec for polymorphic types where the concrete type is determined by the field (e.g. item components keyed by their component type).
 * The codec of the constructed value must be a field codec that does not redirect back to this codec.
 * <p>
 * The supported tags are all tags any child codec may produce.
 * This codec must therefore not be used as element codec of a list, since the first tag would not describe the type of the elements.
 * @param <T>
 */
public class ConstructAndRedirectFieldCodec<T extends NBTSerializable> extends AbstractCodec<T> {

	private final List<TagType> tags;
	
	public ConstructAndRedirectFieldCodec(Class<?> clazz, Constructor<T> constructor, Supplier<T> defaultConstructor, List<TagType> tags) {
		super(clazz, constructor, defaultConstructor, true);
		if (constructor != null && !constructor.requiredField())
			throw new IllegalArgumentException("Must be a constructor that required a field!");
		if (tags.isEmpty())
			throw new IllegalArgumentException("Tags can not be empty!");
		this.tags = List.copyOf(tags);
	}
	
	public Constructor<T> getConstructor() {
		return constructor;
	}
	
	public Supplier<T> getDefaultConstructor() {
		return defaultConstructor;
	}

	@Override
	public boolean serialize(CharSequence key, T value, NBTWriter output, CodecContext context) throws IOException {
		return getRedirect(value).serialize(key, value, output, context);
	}

	@Override
	public T deserialize(T value, NBTReader input, CodecContext context) throws IOException {
		if (value == null) {
			if (constructor != null)
				value = constructor.construct(input, context);
			if (value == null && defaultConstructor != null)
				value = defaultConstructor.get();
			if (value == null)
				throw new NBTException("Unable to construct type!");
		}
		final NBTCodec<T> handler = getRedirect(value);
		final TagType actual = input.getType();
		if (!handler.getTags().contains(actual))
			throw new NBTException("Field " + input.getFieldName() + " (" + value.getClass().getName() + ") does not support tag "
					+ actual + ", expected one of " + handler.getTags());
		return handler.deserialize(value, input, context);
	}

	private NBTCodec<T> getRedirect(T value) throws NBTException {
		@SuppressWarnings("unchecked")
		final NBTCodec<T> handler = (NBTCodec<T>) value.getNBTCodec();
		if (handler == null || handler == this)
			throw new NBTException("Codec for " + value.getClass().getName() + " redirects to itself or is missing!");
		return handler;
	}

	@Override
	public List<TagType> getTags() {
		return tags;
	}

	@Override
	public boolean isReuseValue() {
		return true;
	}

}
