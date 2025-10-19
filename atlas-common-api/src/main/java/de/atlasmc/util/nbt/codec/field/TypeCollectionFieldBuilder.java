package de.atlasmc.util.nbt.codec.field;

import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;

import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.codec.NBTCodec;
import de.atlasmc.util.nbt.codec.type.FieldType;

public class TypeCollectionFieldBuilder<T, V> extends AbstractCollectionFieldBuilder<T, Collection<V>, NBTCodec<V>, TypeCollectionFieldBuilder<T, V>> {

	private BiConsumer<T, V> setter;
	
	public BiConsumer<T, V> getSetter() {
		return setter;
	}
	
	public TypeCollectionFieldBuilder<T, V> setSetter(BiConsumer<T, V> setter) {
		this.setter = setter;
		return this;
	}
	
	@Override
	public NBTField<T> build() {
		return new TypeCollectionField<>(this);
	}

	@Override
	public List<TagType> getTypes() {
		return FieldType.LIST;
	}

	@Override
	protected TypeCollectionFieldBuilder<T, V> getThis() {
		return this;
	}
	
	@Override
	public void clear() {
		super.clear();
		setter = null;
	}

}
