package de.atlasmc.nbt.codec.field;

import java.util.List;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.NBTCodec;

public class UpdatingCodecFieldBuilder<T, V> extends AbstractUpdatingFieldBuilder<T, V, NBTCodec<V>, UpdatingCodecFieldBuilder<T, V>> {
	
	@Override
	public List<TagType> getTypes() {
		return getFieldType().getTags();
	}
	
	@Override
	public UpdatingCodecField<T, V> build() {
		return new UpdatingCodecField<>(this);
	}

	@Override
	protected UpdatingCodecFieldBuilder<T, V> getThis() {
		return this;
	}
	
}
