package de.atlasmc.nbt.codec.field;

import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.CodecTags;
import de.atlasmc.nbt.codec.NBTSerializable;

public class CodecCollectionInnerSearchKeyFieldBuilder<T, V extends NBTSerializable, C extends Namespaced> extends AbstractUpdatingFieldBuilder<T, Collection<V>, BiFunction<T, C, V>, CodecCollectionInnerSearchKeyFieldBuilder<T,V,C>> {

	private CharSequence keyField;
	private Function<NamespacedKey, C> keySupplier;
	private Function<V, C> keyReverse;
	
	public CharSequence getKeyField() {
		return keyField;
	}
	
	public CodecCollectionInnerSearchKeyFieldBuilder<T, V, C> setKeyField(CharSequence keyField) {
		this.keyField = keyField;
		return this;
	}
	
	public Function<V, C> getKeyReverse() {
		return keyReverse;
	}
	
	public CodecCollectionInnerSearchKeyFieldBuilder<T, V, C> setKeyReverse(Function<V, C> keyReverse) {
		this.keyReverse = keyReverse;
		return this;
	}
	
	public Function<NamespacedKey, C> getKeySupplier() {
		return keySupplier;
	}
	
	public CodecCollectionInnerSearchKeyFieldBuilder<T, V, C> setKeySupplier(Function<NamespacedKey, C> keySupplier) {
		this.keySupplier = keySupplier;
		return this;
	}
	
	@Override
	public NBTField<T> build() {
		return new CodecCollectionInnerSearchKeyField<>(this);
	}

	@Override
	public List<TagType> getTypes() {
		return CodecTags.LIST;
	}

	@Override
	protected CodecCollectionInnerSearchKeyFieldBuilder<T, V, C> getThis() {
		return this;
	}

}
