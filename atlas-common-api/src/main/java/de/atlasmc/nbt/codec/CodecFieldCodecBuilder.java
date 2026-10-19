package de.atlasmc.nbt.codec;

import java.util.function.BiConsumer;
import java.util.function.Function;

public class CodecFieldCodecBuilder<T, V> extends AbstractCodecBuilder<T, CodecFieldCodecBuilder<T, V>> {

	private NBTCodec<V> fieldCodec;
	private Function<T, V> getter;
	private BiConsumer<T, V> setter;

	public CodecFieldCodecBuilder(Class<T> type) {
		super(type);
	}
	
	public Function<T, V> getter() {
		return getter;
	}

	public BiConsumer<T, V> setter() {
		return setter;
	}
	
	public NBTCodec<V> fieldCodec() {
		return fieldCodec;
	}
	
	public CodecFieldCodecBuilder<T, V> fieldCodec(NBTCodec<V> fieldCodec) {
		this.fieldCodec = fieldCodec;
		return this;
	}
	
	public CodecFieldCodecBuilder<T, V> getter(Function<T, V> getter) {
		this.getter = getter;
		return this;
	}
	
	public CodecFieldCodecBuilder<T, V> setter(BiConsumer<T, V> setter) {
		this.setter = setter;
		return this;
	}

	@Override
	public CodecFieldCodec<T, V> build() {
		return new CodecFieldCodec<>(this);
	}

	@Override
	protected CodecFieldCodecBuilder<T, V> getThis() {
		return this;
	}
	
}
