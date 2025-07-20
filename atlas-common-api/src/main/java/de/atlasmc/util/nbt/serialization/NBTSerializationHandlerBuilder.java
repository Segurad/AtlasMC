package de.atlasmc.util.nbt.serialization;

import java.util.function.Function;
import java.util.function.Supplier;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.registry.Registry;
import de.atlasmc.util.Builder;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.nbt.serialization.constructor.Constructor;
import de.atlasmc.util.nbt.serialization.constructor.FieldKeyConstructor;
import de.atlasmc.util.nbt.serialization.constructor.SearchFieldEnumConstructor;
import de.atlasmc.util.nbt.serialization.constructor.SearchFieldRegistryConstructor;

public class NBTSerializationHandlerBuilder<T> extends AbstractNBTCompoundFieldBuilder<T, NBTSerializationHandlerBuilder<T>>  implements Builder<NBTSerializationHandler<T>> {
	
	private final Class<T> clazz;
	private Constructor<T> constructor;
	private Supplier<T> defaultConstructor;
	
	protected NBTSerializationHandlerBuilder(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	@Override
	public NBTSerializationHandler<T> build() {
		// TODO Auto-generated method stub
		return null;
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
	
	public <K> NBTSerializationHandlerBuilder<T> fieldKeyConstructor(Registry<K> registry, Function<K, T> constructor) {
		this.constructor = new FieldKeyConstructor<>(registry, constructor);
		return this;
	}
	
	public <K extends Namespaced> NBTSerializationHandlerBuilder<T> searchKeyConstructor(CharSequence keyField, Registry<K> registry, Function<K, T> constructor, Function<T, K> keyReverseSupplier) {
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
