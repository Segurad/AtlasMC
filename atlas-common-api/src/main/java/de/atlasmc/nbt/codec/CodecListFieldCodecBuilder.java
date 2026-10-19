package de.atlasmc.nbt.codec;

import java.util.List;

public class CodecListFieldCodecBuilder<T, V> extends AbstractCollectionFieldCodecBuilder<T, List<V>, NBTCodec<V>, CodecListFieldCodecBuilder<T, V>> {

	private boolean optional;
	
	public CodecListFieldCodecBuilder(Class<T> type) {
		super(type);
	}
	
	public boolean optional() {
		return optional;
	}
	
	public CodecListFieldCodecBuilder<T, V> optional(boolean optional) {
		this.optional = optional;
		return this;
	}

	@Override
	public CodecListFieldCodec<T, V> build() {
		return new CodecListFieldCodec<>(this);
	}

	@Override
	protected CodecListFieldCodecBuilder<T, V> getThis() {
		return this;
	}

}
