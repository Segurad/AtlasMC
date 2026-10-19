package de.atlasmc.nbt.codec;

import java.util.function.Function;

import de.atlasmc.util.function.ToBooleanFunction;

/**
 * 
 * @param <T> type of the codec
 * @param <C> collection type
 * @param <V> collection value
 * @param <B> builder
 */
public abstract class AbstractCollectionFieldCodecBuilder<T, C, V, B extends AbstractCollectionFieldCodecBuilder<T, C, V, B>> extends AbstractCodecBuilder<T, B> {

	private ToBooleanFunction<T> hasData;
	private Function<T, C> getter;
	private V fieldType;
	
	protected AbstractCollectionFieldCodecBuilder(Class<T> type) {
		super(type);
	}
	
	public Function<T, C> getter() {
		return getter;
	}
	
	public B getter(Function<T, C> getter) {
		this.getter = getter;
		return getThis();
	}
	
	public ToBooleanFunction<T> hasData() {
		return hasData;
	}
	
	public V getFieldType() {
		return fieldType;
	}
	
	public B setFieldType(V fieldType) {
		this.fieldType = fieldType;
		return getThis();
	}
	
	public B hasData(ToBooleanFunction<T> hasData) {
		this.hasData = hasData;
		return getThis();
	}
	
}
