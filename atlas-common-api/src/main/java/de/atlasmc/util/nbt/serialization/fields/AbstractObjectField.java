package de.atlasmc.util.nbt.serialization.fields;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;

import de.atlasmc.util.nbt.TagType;

public abstract class AbstractObjectField<T, K> extends NBTField<T> {
	
	protected final Function<T, K> get;
	protected final BiConsumer<T, K> set;
	
	public AbstractObjectField(CharSequence key, List<TagType> types, Function<T, K> get, BiConsumer<T, K> set) {
		this(key, types, get, set, true);
	}
	
	public AbstractObjectField(CharSequence key, List<TagType> types, Function<T, K> get, BiConsumer<T, K> set, boolean useDefault) {
		super(key, types, useDefault);
		this.get = get;
		this.set = set;
	}

}
