package de.atlasmc.io.codec;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ObjIntConsumer;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import de.atlasmc.IDHolder;
import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.io.codec.constructor.Constructor;
import de.atlasmc.io.codec.constructor.RegistryVarIntConstructor;
import de.atlasmc.registry.ProtocolRegistryValue;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.util.Builder;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.function.ObjBooleanConsumer;
import de.atlasmc.util.function.ObjFloatConsumer;
import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.function.ToFloatFunction;
import de.atlasmc.util.nbt.codec.NBTCodec;
import de.atlasmc.util.nbt.codec.NBTSerializable;

public class StreamCodecBuilder<T> implements Builder<StreamCodec<T>>{

	private final Class<T> clazz;
	private Constructor<T> constructor;
	private Supplier<T> defaultConstructor;
	
	public StreamCodecBuilder(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public <K extends ProtocolRegistryValue> StreamCodecBuilder<T> registryVarIntConstructor(RegistryKey<K> registry, Function<K, T> constructor, Function<T, K> keyReverseSupplier) {
		this.constructor = new RegistryVarIntConstructor<>(registry, constructor, keyReverseSupplier);
		return this;
	}
	
	public StreamCodecBuilder<T> defaultConstructor(Supplier<T> defaultConstructor) {
		this.defaultConstructor = defaultConstructor;
		return this;
	}
	
	public StreamCodecBuilder<T> include(StreamCodec<? super T> include) {
		// TODO include
		return this;
	}
	
	public <V, E extends Enum<E> & IDHolder> StreamCodecBuilder<T> enumValueOrCodec(Function<T, V> get, BiConsumer<T, V> set, Class<E> clazz, StreamCodec<? extends V> codec) {
		
		return this;
	}
	
	public <V extends ProtocolRegistryValue> StreamCodecBuilder<T> registryValueOrCodec(Function<T, V> get, BiConsumer<T, V> set, RegistryKey<V> registry, StreamCodec<? extends V> codec) {
		
		return this;
	}
	
	public <V extends Enum<V> & IDHolder> StreamCodecBuilder<T> varIntEnum(Function<T, V> get, BiConsumer<T, V> set, Class<V> clazz) {
		// add field
		return this;
	}
	
	public StreamCodecBuilder<T> varInt(ToIntFunction<T> get, ObjIntConsumer<T> set) {
		// add field
		return this;
	}
	
	public StreamCodecBuilder<T> booleanValue(ToBooleanFunction<T> get, ObjBooleanConsumer<T> set) {
		return this;
	}
	
	public StreamCodecBuilder<T> namespacedKey(Function<T, NamespacedKey> get, BiConsumer<T, NamespacedKey> set) {
		return this;
	}
	
	public StreamCodecBuilder<T> floatValue(ToFloatFunction<T> get, ObjFloatConsumer<T> set) {
		// add field
		return this;
	}
	
	/**
	 * Makes the next field optional
	 * @param <V>
	 * @param has
	 * @return
	 */
	public <V> StreamCodecBuilder<T> optional(ToBooleanFunction<T> has) {
		return this;
	}
	
	public <V extends StreamSerializable> StreamCodecBuilder<T> listCodec(ToBooleanFunction<T> has, Function<T, List<V>> get, StreamCodec<V> codec) {
		return this;
	}
	
	public <V> StreamCodecBuilder<T> codec(Function<T, V> get, BiConsumer<T, V> set, StreamCodec<V> codec) {
		return this;
	}
	
	public <V extends NBTSerializable> StreamCodecBuilder<T> codec(Function<T, V> get, BiConsumer<T, V> set, NBTCodec<V> codec) {
		return this;
	}
	
	public <V> StreamCodecBuilder<T> varIntToObject(Function<T, V> get, BiConsumer<T, V> set, IntFunction<V> toObject, ToIntFunction<V> toInt) {
		return this;
	}
	
	public <V> StreamCodecBuilder<T> intToObject(Function<T, V> get, BiConsumer<T, V> set, IntFunction<V> toObject, ToIntFunction<V> toInt) {
		return this;
	}
	
	public <V extends Namespaced> StreamCodecBuilder<T> dataSet(Function<T, DataSet<V>> get, BiConsumer<T, DataSet<V>> set, RegistryKey<V> registry) {
		return this;
	}

	@Override
	public StreamCodec<T> build() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}
	
}
