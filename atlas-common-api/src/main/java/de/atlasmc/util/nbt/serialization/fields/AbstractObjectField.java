package de.atlasmc.util.nbt.serialization.fields;

import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.util.nbt.TagType;

public abstract class AbstractObjectField<T, K> extends NBTField<T> {
	
	protected final Function<T, K> supplier;
	protected final BiConsumer<T, K> consumer;
	
	public AbstractObjectField(CharSequence key, TagType type, Function<T, K> supplier, BiConsumer<T, K> consumer) {
		this(key, type, supplier, consumer, true);
	}
	
	public AbstractObjectField(CharSequence key, TagType type, Function<T, K> supplier, BiConsumer<T, K> consumer, boolean useDefault) {
		super(key, type, useDefault);
		this.supplier = supplier;
		this.consumer = consumer;
	}

}
