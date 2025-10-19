package de.atlasmc.util.nbt.codec.field;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.codec.type.ObjectType;

public class ObjectFieldBuilder<T, V> extends NBTFieldBuilder<T, ObjectFieldBuilder<T,V>> {

	private BiConsumer<T, V> setter;
	private V defaultValue;
	private boolean reuseValue;
	private Function<T, V> getter;
	private ObjectType<V> fieldType;
	
	public Function<T, V> getGetter() {
		return getter;
	}
	
	public ObjectFieldBuilder<T, V> setGetter(Function<T, V> getter) {
		this.getter = getter;
		return getThis();
	}
	
	public ObjectType<V> getFieldType() {
		return fieldType;
	}
	
	public ObjectFieldBuilder<T, V> setFieldType(ObjectType<V> fieldType) {
		this.fieldType = fieldType;
		return getThis();
	}
	
	@Override
	public List<TagType> getTypes() {
		return fieldType.getTypes();
	}
	
	public boolean isReuseValue() {
		return reuseValue;
	}
	
	public ObjectFieldBuilder<T, V> setReuseValue(boolean reuseValue) {
		this.reuseValue = reuseValue;
		return this;
	}
	
	public ObjectFieldBuilder<T, V> setSetter(BiConsumer<T, V> setter) {
		this.setter = setter;
		return this;
	}
	
	public BiConsumer<T, V> getSetter() {
		return setter;
	}
	
	@Override
	public ObjectField<T, V> build() {
		return new ObjectField<>(this);
	}

	@Override
	protected ObjectFieldBuilder<T, V> getThis() {
		return this;
	}
	
	public V getDefaultValue() {
		return defaultValue;
	}
	
	public ObjectFieldBuilder<T, V> setDefaultValue(V defaultValue) {
		this.defaultValue = defaultValue;
		return this;
	}
	
	@Override
	public void clear() {
		super.clear();
		defaultValue = null;
		fieldType = null;
		getter = null;
		reuseValue = false;
		setter = null;
	}

}
