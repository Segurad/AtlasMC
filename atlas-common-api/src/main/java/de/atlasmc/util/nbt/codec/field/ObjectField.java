package de.atlasmc.util.nbt.codec.field;

import java.io.IOException;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.nbt.codec.type.ObjectType;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;

public class ObjectField<T, V> extends NBTField<T>{

	protected final Function<T, V> getter;
	protected final ObjectType<V> fieldType;
	private final BiConsumer<T, V> setter;
	private final V defaultValue;
	private final boolean reuseValue;
	
	public ObjectField(ObjectFieldBuilder<T, V> builder) {
		super(builder);
		this.getter = Objects.requireNonNull(builder.getGetter());
		this.fieldType = Objects.requireNonNull(builder.getFieldType());
		this.setter = Objects.requireNonNull(builder.getSetter());
		this.defaultValue = builder.getDefaultValue();
		this.reuseValue = builder.isReuseValue();
	}

	@Override
	public boolean serialize(T type, NBTWriter writer, CodecContext context) throws IOException {
		if (serverOnly && context.clientSide)
			return true;
		V value = getter.apply(type);
		if (value == null || value.equals(defaultValue))
			return true;
		return fieldType.serialize(key, value, writer, context);
	}

	@Override
	public void deserialize(T type, NBTReader reader, CodecContext context) throws IOException {
		if (serverOnly && context.clientSide)
			return; // do not deserialize fields send by client that are server only
		setter.accept(type, fieldType.deserialize(reuseValue ? getter.apply(type) : null, reader, context));
	}

}
