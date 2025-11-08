package de.atlasmc.nbt.codec.field;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.NBTCodec;

public class CodecFieldBuilder<T, V> extends NBTFieldBuilder<T, CodecFieldBuilder<T,V>> {

	private BiConsumer<T, V> setter;
	private V defaultValue;
	private Function<T, V> getter;
	private NBTCodec<? extends V> codec;
	
	public Function<T, V> getGetter() {
		return getter;
	}
	
	public CodecFieldBuilder<T, V> setGetter(Function<T, V> getter) {
		this.getter = getter;
		return getThis();
	}
	
	@SuppressWarnings("unchecked")
	public NBTCodec<V> getCodec() {
		return (NBTCodec<V>) codec;
	}
	
	public CodecFieldBuilder<T, V> setCodec(NBTCodec<? extends V> codec) {
		this.codec = codec;
		return getThis();
	}
	
	@Override
	public List<TagType> getTypes() {
		return codec.getTags();
	}
	
	public CodecFieldBuilder<T, V> setSetter(BiConsumer<T, V> setter) {
		this.setter = setter;
		return this;
	}
	
	public BiConsumer<T, V> getSetter() {
		return setter;
	}
	
	@Override
	public CodecField<T, V> build() {
		return new CodecField<>(this);
	}

	@Override
	protected CodecFieldBuilder<T, V> getThis() {
		return this;
	}
	
	public V getDefaultValue() {
		return defaultValue;
	}
	
	public CodecFieldBuilder<T, V> setDefaultValue(V defaultValue) {
		this.defaultValue = defaultValue;
		return this;
	}
	
	@Override
	public void clear() {
		super.clear();
		defaultValue = null;
		codec = null;
		getter = null;
		setter = null;
	}

}
