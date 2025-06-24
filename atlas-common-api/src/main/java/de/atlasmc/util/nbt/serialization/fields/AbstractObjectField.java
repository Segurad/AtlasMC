package de.atlasmc.util.nbt.serialization.fields;

import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.util.nbt.TagType;

public abstract class AbstractObjectField<T, K> extends NBTField<T> {
	
	protected final Function<T, K> get;
	protected final BiConsumer<T, K> set;
	
	public AbstractObjectField(CharSequence key, TagType type, Function<T, K> get, BiConsumer<T, K> set) {
		this(key, type, get, set, true);
	}
	
	public AbstractObjectField(CharSequence key, TagType type, Function<T, K> get, BiConsumer<T, K> set, boolean useDefault) {
		super(key, type, useDefault);
		this.get = get;
		this.set = set;
	}

}
