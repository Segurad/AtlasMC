package de.atlasmc.util.nbt.serialization.fields;

import java.util.List;
import java.util.function.Function;

import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.nbt.TagType;

public abstract class AbstractCollectionField<T, K> extends NBTField<T> {

	protected final ToBooleanFunction<T> has;
	protected final Function<T, K> get;
	
	public AbstractCollectionField(CharSequence key, List<TagType> types, ToBooleanFunction<T> has, Function<T, K> get, boolean optional) {
		super(key, types, optional);
		this.has = has;
		this.get = get;
	}

}
