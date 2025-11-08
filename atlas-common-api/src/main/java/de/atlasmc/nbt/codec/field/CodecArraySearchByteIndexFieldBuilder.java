package de.atlasmc.nbt.codec.field;

import java.util.List;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.CodecTags;
import de.atlasmc.nbt.codec.NBTCodec;

public class CodecArraySearchByteIndexFieldBuilder<T, V> extends AbstractCollectionFieldBuilder<T, V[], NBTCodec<V>, CodecArraySearchByteIndexFieldBuilder<T, V>> {

	private CharSequence indexKey;
	
	public CharSequence getIndexKey() {
		return indexKey;
	}
	
	public CodecArraySearchByteIndexFieldBuilder<T, V> setIndexKey(CharSequence indexKey) {
		this.indexKey = indexKey;
		return this;
	}
	
	@Override
	public NBTField<T> build() {
		return new CodecArraySearchByteIndexField<>(this);
	}

	@Override
	public List<TagType> getTypes() {
		return CodecTags.LIST;
	}

	@Override
	protected CodecArraySearchByteIndexFieldBuilder<T, V> getThis() {
		return this;
	}

}
