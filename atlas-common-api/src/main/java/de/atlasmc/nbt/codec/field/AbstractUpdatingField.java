package de.atlasmc.nbt.codec.field;

import java.util.Objects;
import java.util.function.Function;

import de.atlasmc.util.function.ToBooleanFunction;

/**
 * Field that updates a existing value.
 * For writing has a function to check whether or not data is present to prevent initialization of data
 * @param <T>
 * @param <C>
 * @param <V>
 */
public abstract class AbstractUpdatingField<T, C, V> extends NBTField<T> {

	protected final ToBooleanFunction<T> hasData;
	protected final Function<T, C> getter;
	protected final V fieldType;
	
	public AbstractUpdatingField(AbstractUpdatingFieldBuilder<T, C, V, ?> builder) {
		super(builder);
		this.hasData = Objects.requireNonNull(builder.getHasData());
		this.getter = Objects.requireNonNull(builder.getGetter());
		this.fieldType = Objects.requireNonNull(builder.getFieldType());
	}

}
