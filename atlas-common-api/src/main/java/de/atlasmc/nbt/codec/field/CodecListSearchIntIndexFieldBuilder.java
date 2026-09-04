package de.atlasmc.nbt.codec.field;

import java.util.List;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.CodecTags;
import de.atlasmc.nbt.codec.NBTCodec;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;

public class CodecListSearchIntIndexFieldBuilder<T, V> extends AbstractUpdatingFieldBuilder<T, Int2ObjectMap<V>, NBTCodec<V>, CodecListSearchIntIndexFieldBuilder<T, V>> {

	private CharSequence indexKey;
	
	public CharSequence getIndexKey() {
		return indexKey;
	}
	
	public CodecListSearchIntIndexFieldBuilder<T, V> setIndexKey(CharSequence indexKey) {
		this.indexKey = indexKey;
		return this;
	}
	
	@Override
	public NBTField<T> build() {
		return new CodecListSearchIntIndexField<>(this);
	}

	@Override
	public List<TagType> getTypes() {
		return CodecTags.LIST;
	}

	@Override
	protected CodecListSearchIntIndexFieldBuilder<T, V> getThis() {
		return this;
	}

}
