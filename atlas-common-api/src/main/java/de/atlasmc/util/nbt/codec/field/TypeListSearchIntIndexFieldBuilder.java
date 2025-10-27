package de.atlasmc.util.nbt.codec.field;

import java.util.List;

import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.codec.NBTCodec;
import de.atlasmc.util.nbt.codec.type.FieldType;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public class TypeListSearchIntIndexFieldBuilder<T, V> extends AbstractCollectionFieldBuilder<T, Int2ObjectMap<V>, NBTCodec<V>, TypeListSearchIntIndexFieldBuilder<T, V>> {

	private CharSequence indexKey;
	
	public CharSequence getIndexKey() {
		return indexKey;
	}
	
	public TypeListSearchIntIndexFieldBuilder<T, V> setIndexKey(CharSequence indexKey) {
		this.indexKey = indexKey;
		return this;
	}
	
	@Override
	public NBTField<T> build() {
		return new TypeListSearchIntIndexField<>(this);
	}

	@Override
	public List<TagType> getTypes() {
		return FieldType.LIST;
	}

	@Override
	protected TypeListSearchIntIndexFieldBuilder<T, V> getThis() {
		return this;
	}
	
	@Override
	public void clear() {
		super.clear();
		indexKey = null;
	}

}
