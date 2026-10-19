package de.atlasmc.nbt.codec;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.nbt.codec.constructor.Constructor;
import de.atlasmc.nbt.codec.constructor.FieldKeyConstructor;
import de.atlasmc.nbt.codec.constructor.FieldKeyRegistryConstructor;
import de.atlasmc.nbt.codec.constructor.SearchFieldEnumConstructor;
import de.atlasmc.nbt.codec.constructor.SearchFieldRegistryConstructor;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.util.AtlasUtil;
import de.atlasmc.util.Builder;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.enums.EnumName;

public abstract class AbstractCodecBuilder<T, B extends AbstractCodecBuilder<T, B>> implements Builder<NBTCodec<T>> {

	protected final Class<T> type;
	protected Constructor<T> constructor;
	protected Supplier<T> defaultConstructor;
	protected Boolean redirectAfterConstruction;
	
	protected AbstractCodecBuilder(@NotNull Class<T> type) {
		this.type = Objects.requireNonNull(type, "type");
	}
	
	@SuppressWarnings("unchecked")
	public B include(ConstructAndRedirectFieldCodec<? super T> codec) {
		AtlasUtil.requireAssignable(codec.getType(), type);
		return this.constructor((Constructor<T>) codec.getConstructor())
			.defaultConstructor((Supplier<T>) codec.getDefaultConstructor());
	}
	
	public Class<T> getType() {
		return type;
	}
	
	public Constructor<T> constructor() {
		return constructor;
	}
	
	public Supplier<T> defaultConstructor() {
		return defaultConstructor;
	}
	
	public boolean isRedirectAfterConstruction() {
		return redirectAfterConstruction == null ? constructor != null && NBTSerializable.class.isAssignableFrom(type) : redirectAfterConstruction;
	}
	
	public B redirectAfterConstruction(Boolean redirectAfterConstruction) {
		this.redirectAfterConstruction = redirectAfterConstruction;
		return getThis();
	}
	
	public B constructor(Constructor<T> constructor) {
		this.constructor = constructor;
		return getThis();
	}
	
	public B fieldKeyConstructor(Function<String, T> constructor) {
		this.constructor = new FieldKeyConstructor<>(constructor);
		return getThis();
	}
	
	public <K> B fieldKeyRegistryConstructor(RegistryKey<K> registry, Function<K, T> constructor) {
		this.constructor = new FieldKeyRegistryConstructor<>(registry, constructor);
		return getThis();
	}
	
	public <K extends Namespaced> B searchKeyConstructor(CharSequence keyField, RegistryKey<K> registry, Function<K, T> constructor, Function<T, K> keyReverseSupplier) {
		this.constructor = new SearchFieldRegistryConstructor<>(keyField, registry, constructor, keyReverseSupplier);
		return getThis();
	}
	
	public <K extends Enum<K> & EnumName> B searchKeyEnumConstructor(CharSequence keyField, Class<K> clazz, Function<K, T> constructor, Function<T, K> keyReverseSupplier) {
		this.constructor = new SearchFieldEnumConstructor<>(keyField, clazz, constructor, keyReverseSupplier);
		return getThis();
	}
	
	public B defaultConstructor(Supplier<T> defaultConstructor) {
		this.defaultConstructor = defaultConstructor;
		return getThis();
	}
	
	protected abstract B getThis();
	
}
