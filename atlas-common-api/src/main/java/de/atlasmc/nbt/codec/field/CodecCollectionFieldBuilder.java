package de.atlasmc.nbt.codec.field;

import java.util.List;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.NBTCodec;

public class CodecCollectionFieldBuilder<T, V> extends AbstractCollectionFieldBuilder<T, V, NBTCodec<V>, CodecCollectionFieldBuilder<T, V>> {
	
	@Override
	public List<TagType> getTypes() {
		return getFieldType().getTags();
	}
	
	@Override
	public CodecCollectionField<T, V> build() {
		return new CodecCollectionField<>(this);
	}

	@Override
	protected CodecCollectionFieldBuilder<T, V> getThis() {
		return this;
	}
	
}
