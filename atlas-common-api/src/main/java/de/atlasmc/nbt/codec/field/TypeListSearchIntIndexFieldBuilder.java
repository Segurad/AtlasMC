package de.atlasmc.nbt.codec.field;

import java.util.List;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.CodecTags;
import de.atlasmc.nbt.codec.NBTCodec;
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
		return CodecTags.LIST;
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
