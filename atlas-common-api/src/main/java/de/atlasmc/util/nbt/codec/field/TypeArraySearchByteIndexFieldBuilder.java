package de.atlasmc.util.nbt.codec.field;

import java.util.List;

import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.codec.NBTCodec;
import de.atlasmc.util.nbt.codec.type.FieldType;

public class TypeArraySearchByteIndexFieldBuilder<T, V> extends AbstractCollectionFieldBuilder<T, V[], NBTCodec<V>, TypeArraySearchByteIndexFieldBuilder<T, V>> {

	private CharSequence indexKey;
	
	public CharSequence getIndexKey() {
		return indexKey;
	}
	
	public TypeArraySearchByteIndexFieldBuilder<T, V> setIndexKey(CharSequence indexKey) {
		this.indexKey = indexKey;
		return this;
	}
	
	@Override
	public NBTField<T> build() {
		return new TypeArraySearchByteIndexField<>(this);
	}

	@Override
	public List<TagType> getTypes() {
		return FieldType.LIST;
	}

	@Override
	protected TypeArraySearchByteIndexFieldBuilder<T, V> getThis() {
		return this;
	}
	
	@Override
	public void clear() {
		super.clear();
		indexKey = null;
	}

}
