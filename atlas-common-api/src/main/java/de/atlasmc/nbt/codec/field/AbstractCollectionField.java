package de.atlasmc.nbt.codec.field;

import java.util.Objects;
import java.util.function.Function;

import de.atlasmc.util.function.ToBooleanFunction;

public abstract class AbstractCollectionField<T, C, V> extends NBTField<T> {

	protected final ToBooleanFunction<T> hasData;
	protected final Function<T, C> getter;
	protected final V fieldType;
	
	public AbstractCollectionField(AbstractCollectionFieldBuilder<T, C, V, ?> builder) {
		super(builder);
		this.hasData = Objects.requireNonNull(builder.getHasData(), "hasData");
		this.getter = Objects.requireNonNull(builder.getGetter(), "getter");
		this.fieldType = Objects.requireNonNull(builder.getFieldType(), "fieldType");
	}

}
