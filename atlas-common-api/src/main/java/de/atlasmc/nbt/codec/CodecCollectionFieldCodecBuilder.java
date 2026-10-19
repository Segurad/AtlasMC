package de.atlasmc.nbt.codec;

public class CodecCollectionFieldCodecBuilder<T, C> extends AbstractCollectionFieldCodecBuilder<T, C, NBTCodec<C>, CodecCollectionFieldCodecBuilder<T, C>> {
	
	public CodecCollectionFieldCodecBuilder(Class<T> type) {
		super(type);
	}

	@Override
	public CodecCollectionFieldCodec<T, C> build() {
		return new CodecCollectionFieldCodec<>(this);
	}

	@Override
	protected CodecCollectionFieldCodecBuilder<T, C> getThis() {
		return this;
	}
	
}
