package de.atlasmc.nbt.codec;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Supplier;

import de.atlasmc.nbt.NBTException;
import de.atlasmc.nbt.codec.constructor.Constructor;
import de.atlasmc.nbt.io.NBTReader;
import de.atlasmc.util.codec.CodecContext;

public abstract class AbstractCodec<T> implements NBTCodec<T> {

	protected final Constructor<T> constructor;
	protected final Supplier<T> defaultConstructor;
	protected final Class<?> clazz;
	protected final boolean redirectAfterConstruction;
	
	public AbstractCodec(Class<?> type, Constructor<T> constructor, Supplier<T> defaultConstructor, boolean redirectAfterConstruction) {
		this.clazz = Objects.requireNonNull(type, "type");
		this.constructor = constructor;
		this.defaultConstructor = defaultConstructor;
		this.redirectAfterConstruction = redirectAfterConstruction;
	}
	
	public AbstractCodec(AbstractCodecBuilder<T, ?> builder) {
		this(builder.getType(),
				builder.constructor(),
				builder.defaultConstructor(),
				builder.isRedirectAfterConstruction());
	}
	
	@Override
	public Class<?> getType() {
		return clazz;
	}

	/**
	 * Constructs the type using the constructor or default constructor
	 * @param input
	 * @param context
	 * @return type
	 * @throws IOException
	 * @throws {@link NBTException} if there was no value type constructed
	 */
	protected T construct(NBTReader input, CodecContext context) throws IOException {
		T value = null;
		if (constructor != null)
			value = constructor.construct(input, context);
		if (value == null && defaultConstructor != null) {
			value = defaultConstructor.get();
		}
		if (value == null)
			throw new NBTException("Unable to construct type!");
		return value;
	}

}
