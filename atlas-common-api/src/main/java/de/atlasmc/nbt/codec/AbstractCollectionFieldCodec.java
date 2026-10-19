package de.atlasmc.nbt.codec;

import java.util.Objects;
import java.util.function.Function;

import de.atlasmc.util.function.ToBooleanFunction;

public abstract class AbstractCollectionFieldCodec<T, C, V> extends AbstractCodec<T> {
	
	protected final ToBooleanFunction<T> hasData;
	protected final Function<T, C> getter;
	protected final V fieldType;

	public AbstractCollectionFieldCodec(AbstractCollectionFieldCodecBuilder<T, C, V, ?> builder) {
		super(builder);
		this.hasData = Objects.requireNonNull(builder.hasData(), "hasData");
		this.getter = Objects.requireNonNull(builder.getter(), "getter");
		this.fieldType = Objects.requireNonNull(builder.getFieldType(), "fieldType");
	}
	
	@Override
	public boolean isReuseValue() {
		return true;
	}
	
}
