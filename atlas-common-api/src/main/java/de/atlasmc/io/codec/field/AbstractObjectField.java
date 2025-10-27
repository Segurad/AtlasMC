package de.atlasmc.io.codec.field;

import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Function;

public abstract class AbstractObjectField<T, V> extends StreamField<T> {
	
	protected final Function<T, V> get;
	protected final BiConsumer<T, V> set;
	
	public AbstractObjectField(Function<T, V> get, BiConsumer<T, V> set) {
		this.get = Objects.requireNonNull(get);
		this.set = Objects.requireNonNull(set);
	}

}
