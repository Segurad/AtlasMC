package de.atlasmc.nbt.codec;

import java.util.function.Function;
import java.util.function.Supplier;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.nbt.codec.constructor.Constructor;
import de.atlasmc.nbt.codec.constructor.FieldKeyConstructor;
import de.atlasmc.nbt.codec.constructor.FieldKeyRegistryConstructor;
import de.atlasmc.nbt.codec.constructor.SearchFieldEnumConstructor;
import de.atlasmc.nbt.codec.constructor.SearchFieldRegistryConstructor;
import de.atlasmc.nbt.codec.field.NBTCompoundField;
import de.atlasmc.nbt.codec.field.NBTCompoundFieldBuilder;
import de.atlasmc.nbt.codec.field.NBTField;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.util.Builder;
import de.atlasmc.util.enums.EnumName;
import de.atlasmc.util.function.ToBooleanFunction;

public class NBTCodecBuilder<T> implements AbstractNBTCompoundFieldBuilder<T, NBTCodecBuilder<T>>, Builder<NBTCodec<T>> {
	
	private final Class<T> clazz;
	private Constructor<T> constructor;
	private Supplier<T> defaultConstructor;
	private Boolean redirectAfterConstruction;
	private Boolean isField;
	
	protected final NBTCompoundFieldBuilder<T> root = new NBTCompoundFieldBuilder<>();
	protected NBTCompoundFieldBuilder<T> builder = root;
	
	protected NBTCodecBuilder(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public boolean isField() {
		return isField != null ? isField : false;
	}
	
	public Boolean getIsField() {
		return isField;
	}
	
	public NBTCodecBuilder<T> setField(Boolean isField) {
		this.isField = isField;
		return this;
	}
	
	@Override
	public NBTCodecBuilder<T> addField(NBTField<T> field) {
		builder.addField(field);
		return getThis();
	}
	
	public NBTCompoundField<T> buildFields() {
		return root.build();
	}
	
	public Class<T> getType() {
		return clazz;
	}
	
	public Constructor<T> getConstructor() {
		return constructor;
	}
	
	public Supplier<T> getDefaultConstructor() {
		return defaultConstructor;
	}
	
	public void setConstructor(Constructor<T> constructor) {
		this.constructor = constructor;
	}
	
	public boolean isRedirectAfterConstruction() {
		return redirectAfterConstruction == null ? constructor != null && NBTSerializable.class.isAssignableFrom(clazz) : redirectAfterConstruction;
	}
	
	public NBTCodecBuilder<T> setRedirectAfterConstruction(Boolean redirectAfterConstruction) {
		this.redirectAfterConstruction = redirectAfterConstruction;
		return this;
	}
	
	@Override
	public NBTCodec<T> build() {
		return new NBTCodecImpl<>(getThis());
	}

	@Override
	public void clear() {
		this.constructor = null;
	}
	
	public NBTCodecBuilder<T> include(NBTCodec<? super T> include) {
		// TODO include
		return this;
	}
	
	public NBTCodecBuilder<T> constructor(Constructor<T> constructor) {
		this.constructor = constructor;
		return this;
	}
	
	public NBTCodecBuilder<T> fieldKeyConstructor(Function<String, T> constructor) {
		this.constructor = new FieldKeyConstructor<>(constructor);
		return this;
	}
	
	public <K> NBTCodecBuilder<T> fieldKeyRegistryConstructor(RegistryKey<K> registry, Function<K, T> constructor) {
		this.constructor = new FieldKeyRegistryConstructor<>(registry, constructor);
		return this;
	}
	
	public <K extends Namespaced> NBTCodecBuilder<T> searchKeyConstructor(CharSequence keyField, RegistryKey<K> registry, Function<K, T> constructor, Function<T, K> keyReverseSupplier) {
		this.constructor = new SearchFieldRegistryConstructor<>(keyField, registry, constructor, keyReverseSupplier);
		return this;
	}
	
	public <K extends Enum<K> & EnumName> NBTCodecBuilder<T> searchKeyEnumConstructor(CharSequence keyField, Class<K> clazz, Function<K, T> constructor, Function<T, K> keyReverseSupplier) {
		this.constructor = new SearchFieldEnumConstructor<>(keyField, clazz, constructor, keyReverseSupplier);
		return this;
	}
	
	public NBTCodecBuilder<T> defaultConstructor(Supplier<T> defaultConstructor) {
		this.defaultConstructor = defaultConstructor;
		return this;
	}
	
	public NBTCodecBuilder<T> beginComponent(CharSequence key) {
		return beginComponent(key, null, false);
	}
	
	public NBTCodecBuilder<T> beginComponent(CharSequence key, boolean serverOnly) {
		return beginComponent(key, null, serverOnly);
	}
	
	public NBTCodecBuilder<T> beginComponent(CharSequence key, ToBooleanFunction<T> has) {
		return beginComponent(key, has, false);
	}
	
	public NBTCodecBuilder<T> beginComponent(CharSequence key, ToBooleanFunction<T> has, boolean serverOnly) {
		
		builder = new NBTCompoundFieldBuilder<>(key, builder, serverOnly);
		builder.has = has;
		return getThis();
	}
	
	public NBTCodecBuilder<T> endComponent() {
		if (builder.parent == null)
			throw new IllegalStateException("No component to end!");
		builder = builder.parent;
		return getThis();
	}

	@Override
	public NBTCodecBuilder<T> getThis() {
		return this;
	}

}
