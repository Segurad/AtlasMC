package de.atlasmc.nbt.codec.field;

import java.util.List;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.CodecTags;
import de.atlasmc.util.function.ObjBooleanConsumer;
import de.atlasmc.util.function.ObjFloatConsumer;
import de.atlasmc.util.function.ObjShortConsumer;
import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.function.ToFloatFunction;

public class PrimitiveFieldBuilder<T, G, S> extends NBTFieldBuilder<T, PrimitiveFieldBuilder<T, G, S>> {

	private G getter;
	private S setter;
	private Number defaultValue;
	private TagType type;
	
	public PrimitiveFieldBuilder<T, G, S> setType(TagType type) {
		this.type = type;
		return this;
	}
	
	public TagType getType() {
		return type;
	}
	
	public boolean isUseDefault() {
		return defaultValue != null;
	}
	
	public S getSetter() {
		return setter;
	}
	
	public G getGetter() {
		return getter;
	}
	
	public PrimitiveFieldBuilder<T, G, S> setSetter(S setter) {
		this.setter = setter;
		return this;
	}
	
	public PrimitiveFieldBuilder<T, G, S> setGetter(G getter) {
		this.getter = getter;
		return this;
	}
	
	public Number getDefaultValue() {
		return defaultValue;
	}
	
	public PrimitiveFieldBuilder<T, G, S> setDefaultValue(byte defaultValue) {
		this.defaultValue = Byte.valueOf(defaultValue);
		return this;
	}
	
	public PrimitiveFieldBuilder<T, G, S> setDefaultValue(short defaultValue) {
		this.defaultValue = Short.valueOf(defaultValue);;
		return this;
	}
	
	public PrimitiveFieldBuilder<T, G, S> setDefaultValue(int defaultValue) {
		this.defaultValue = Integer.valueOf(defaultValue);
		return this;
	}
	
	public PrimitiveFieldBuilder<T, G, S> setDefaultValue(float defaultValue) {
		this.defaultValue = Float.valueOf(defaultValue);
		return this;
	}
	
	public PrimitiveFieldBuilder<T, G, S> setDefaultValue(double defaultValue) {
		this.defaultValue = Double.valueOf(defaultValue);
		return this;
	}
	
	public PrimitiveFieldBuilder<T, G, S> setDefaultValue(long defaultValue) {
		this.defaultValue = Long.valueOf(defaultValue);
		return this;
	}
	
	public PrimitiveFieldBuilder<T, G, S> setDefaultValue(Number defaultValue) {
		this.defaultValue = defaultValue;
		return this;
	}
	
	public PrimitiveFieldBuilder<T, G, S> setDefaultValue(boolean defaultValue) {
		this.defaultValue = defaultValue ? 1 : 0;
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	public NBTField<T> build() {
		return switch (type) {
		case BYTE -> new ByteField<T>((PrimitiveFieldBuilder<T, ToIntFunction<T>, ObjIntConsumer<T>>) this);
		case SHORT -> new ShortField<T>((PrimitiveFieldBuilder<T, ToIntFunction<T>, ObjShortConsumer<T>>) this);
		case INT -> new IntField<T>((PrimitiveFieldBuilder<T, ToIntFunction<T>, ObjIntConsumer<T>>) this);
		case LONG -> new LongField<T>((PrimitiveFieldBuilder<T, ToLongFunction<T>, ObjLongConsumer<T>>) this);
		case FLOAT -> new FloatField<T>((PrimitiveFieldBuilder<T, ToFloatFunction<T>, ObjFloatConsumer<T>>) this);
		case DOUBLE -> new DoubleField<T>((PrimitiveFieldBuilder<T, ToDoubleFunction<T>, ObjDoubleConsumer<T>>) this);
		case null -> new BooleanField<T>((PrimitiveFieldBuilder<T, ToBooleanFunction<T>, ObjBooleanConsumer<T>>) this);
		default ->
			throw new IllegalArgumentException("Unsupported type: " + type);
		};
	}

	@Override
	public List<TagType> getTypes() {
		return switch (type) {
		case BYTE -> CodecTags.BYTE;
		case SHORT -> CodecTags.SHORT;
		case INT -> CodecTags.INT;
		case LONG -> CodecTags.LONG;
		case FLOAT -> CodecTags.FLOAT;
		case DOUBLE -> CodecTags.DOUBLE;
		case null -> CodecTags.BYTE;
		default ->
			throw new IllegalArgumentException("Unsupported type: " + type);
		};
	}

	@Override
	protected PrimitiveFieldBuilder<T, G, S> getThis() {
		return this;
	}
	
}
