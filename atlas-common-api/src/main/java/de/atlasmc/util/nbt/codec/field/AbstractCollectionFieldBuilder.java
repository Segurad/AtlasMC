package de.atlasmc.util.nbt.codec.field;

import java.util.function.Function;

import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.nbt.codec.type.ObjectType;

public abstract class AbstractCollectionFieldBuilder<T, C, V, B extends AbstractCollectionFieldBuilder<T, C, V, B>> extends NBTFieldBuilder<T, B> {

	private ToBooleanFunction<T> hasData;
	private Function<T, C> getter;
	private ObjectType<V> fieldType;
	
	public Function<T, C> getGetter() {
		return getter;
	}
	
	public B setGetter(Function<T, C> getter) {
		this.getter = getter;
		return getThis();
	}
	
	public ToBooleanFunction<T> getHasData() {
		return hasData;
	}
	
	public ObjectType<V> getFieldType() {
		return fieldType;
	}
	
	public B setFieldType(ObjectType<V> fieldType) {
		this.fieldType = fieldType;
		return getThis();
	}
	
	public B setHasData(ToBooleanFunction<T> hasData) {
		this.hasData = hasData;
		return getThis();
	}
	
}
