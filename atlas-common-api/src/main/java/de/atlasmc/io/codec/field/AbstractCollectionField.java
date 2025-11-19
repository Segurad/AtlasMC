package de.atlasmc.io.codec.field;

import java.util.Objects;
import java.util.function.Function;

import de.atlasmc.util.function.ToBooleanFunction;

public abstract class AbstractCollectionField<T, V> extends StreamField<T> {

	protected final ToBooleanFunction<T> has;
	protected final Function<T, V> get;
	
	public AbstractCollectionField(ToBooleanFunction<T> has, Function<T, V> get) {
		this.has = Objects.requireNonNull(has);
		this.get = Objects.requireNonNull(get);
	}
	
}
