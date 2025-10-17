package de.atlasmc.util.nbt.codec.field;

import java.util.List;

import de.atlasmc.util.nbt.TagType;

public class ObjectListFieldBuilder<T, V> extends AbstractCollectionFieldBuilder<T, List<V>, V, ObjectListFieldBuilder<T, V>> {

	private boolean optional = true;
	
	public boolean isOptional() {
		return optional;
	}
	
	public ObjectListFieldBuilder<T, V> setOptional(boolean optional) {
		this.optional = optional;
		return this;
	}
	
	@Override
	public NBTField<T> build() {
		return new ObjectListField<>(this);
	}

	@Override
	public List<TagType> getTypes() {
		return NBTField.LIST;
	}

	@Override
	protected ObjectListFieldBuilder<T, V> getThis() {
		return this;
	}

}
