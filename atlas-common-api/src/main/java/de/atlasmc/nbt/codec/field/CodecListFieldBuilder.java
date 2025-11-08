package de.atlasmc.nbt.codec.field;

import java.util.List;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.CodecTags;
import de.atlasmc.nbt.codec.NBTCodec;

public class CodecListFieldBuilder<T, V> extends AbstractCollectionFieldBuilder<T, List<V>, NBTCodec<V>, CodecListFieldBuilder<T, V>> {

	private boolean optional = true;
	
	public boolean isOptional() {
		return optional;
	}
	
	public CodecListFieldBuilder<T, V> setOptional(boolean optional) {
		this.optional = optional;
		return this;
	}
	
	@Override
	public NBTField<T> build() {
		return new CodecListField<>(this);
	}

	@Override
	public List<TagType> getTypes() {
		return CodecTags.LIST;
	}

	@Override
	protected CodecListFieldBuilder<T, V> getThis() {
		return this;
	}

}
