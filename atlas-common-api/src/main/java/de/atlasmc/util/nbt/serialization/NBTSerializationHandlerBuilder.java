package de.atlasmc.util.nbt.serialization;

import java.util.function.Function;
import java.util.function.Supplier;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.util.Builder;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.nbt.serialization.constructor.Constructor;
import de.atlasmc.util.nbt.serialization.constructor.FieldKeyConstructor;
import de.atlasmc.util.nbt.serialization.constructor.SearchFieldEnumConstructor;
import de.atlasmc.util.nbt.serialization.constructor.SearchFieldRegistryConstructor;
import de.atlasmc.util.nbt.serialization.fields.NBTCompoundField;

public class NBTSerializationHandlerBuilder<T> extends AbstractNBTCompoundFieldBuilder<T, NBTSerializationHandlerBuilder<T>>  implements Builder<NBTSerializationHandler<T>> {
	
	private final Class<T> clazz;
	private Constructor<T> constructor;
	private Supplier<T> defaultConstructor;
	private Boolean redirectAfterConstruction;
	
	protected NBTSerializationHandlerBuilder(Class<T> clazz) {
		this.clazz = clazz;
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
		return redirectAfterConstruction == null ? NBTSerializable.class.isAssignableFrom(clazz) : redirectAfterConstruction;
	}
	
	public NBTSerializationHandlerBuilder<T> setRedirectAfterConstruction(Boolean redirectAfterConstruction) {
		this.redirectAfterConstruction = redirectAfterConstruction;
		return this;
	}
	
	@Override
	public NBTSerializationHandler<T> build() {
		return new NBTSerializationHandlerImpl<>(getThis());
	}

	@Override
	public void clear() {
		this.constructor = null;
	}
	
	public NBTSerializationHandlerBuilder<T> include(NBTSerializationHandler<? super T> include) {
		return this;
	}
	
	public NBTSerializationHandlerBuilder<T> constructor(Constructor<T> constructor) {
		this.constructor = constructor;
		return this;
	}
	
	public <K> NBTSerializationHandlerBuilder<T> fieldKeyConstructor(RegistryKey<K> registry, Function<K, T> constructor) {
		this.constructor = new FieldKeyConstructor<>(registry, constructor);
		return this;
	}
	
	public <K extends Namespaced> NBTSerializationHandlerBuilder<T> searchKeyConstructor(CharSequence keyField, RegistryKey<K> registry, Function<K, T> constructor, Function<T, K> keyReverseSupplier) {
		this.constructor = new SearchFieldRegistryConstructor<>(keyField, registry, constructor, keyReverseSupplier);
		return this;
	}
	
	public <K extends Enum<?> & EnumName> NBTSerializationHandlerBuilder<T> searchKeyEnumConstructor(CharSequence keyField, Function<String, K> enumSupplier, Function<K, T> constructor, Function<T, K> keyReverseSupplier) {
		this.constructor = new SearchFieldEnumConstructor<>(keyField, enumSupplier, constructor, keyReverseSupplier);
		return this;
	}
	
	public NBTSerializationHandlerBuilder<T> defaultConstructor(Supplier<T> defaultConstructor) {
		this.defaultConstructor = defaultConstructor;
		return this;
	}

	@Override
	protected NBTSerializationHandlerBuilder<T> getThis() {
		return this;
	}

}
