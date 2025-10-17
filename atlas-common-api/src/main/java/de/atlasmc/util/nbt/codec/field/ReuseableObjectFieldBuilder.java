package de.atlasmc.util.nbt.codec.field;

import java.util.List;

import de.atlasmc.util.nbt.TagType;

public class ReuseableObjectFieldBuilder<T, V> extends AbstractCollectionFieldBuilder<T, V, V, ReuseableObjectFieldBuilder<T, V>> {
	
	@Override
	public List<TagType> getTypes() {
		return getFieldType().getTypes();
	}
	
	@Override
	public ReuseableObjectField<T, V> build() {
		return new ReuseableObjectField<>(this);
	}

	@Override
	protected ReuseableObjectFieldBuilder<T, V> getThis() {
		return this;
	}
	
}
