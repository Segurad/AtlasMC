package de.atlasmc.io.codec;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.Supplier;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import de.atlasmc.IDHolder;
import de.atlasmc.NamespacedKey;
import de.atlasmc.io.codec.constructor.Constructor;
import de.atlasmc.io.codec.constructor.EnumVarIntConstructor;
import de.atlasmc.io.codec.constructor.RegistryVarIntConstructor;
import de.atlasmc.io.codec.field.BooleanField;
import de.atlasmc.io.codec.field.CodecCollectionField;
import de.atlasmc.io.codec.field.CodecField;
import de.atlasmc.io.codec.field.CodecListField;
import de.atlasmc.io.codec.field.DoubleField;
import de.atlasmc.io.codec.field.VarIntEnumOrCodecField;
import de.atlasmc.io.codec.field.FloatField;
import de.atlasmc.io.codec.field.HasOptionalField;
import de.atlasmc.io.codec.field.IntField;
import de.atlasmc.io.codec.field.LongField;
import de.atlasmc.io.codec.field.MapRegistryValueToVarIntField;
import de.atlasmc.io.codec.field.MultimapTypeToCodecField;
import de.atlasmc.io.codec.field.NBTCodecField;
import de.atlasmc.io.codec.field.VarIntRegistryOrCodecField;
import de.atlasmc.io.codec.field.StaticBooleanField;
import de.atlasmc.io.codec.field.StreamField;
import de.atlasmc.io.codec.field.VarIntEnumField;
import de.atlasmc.io.codec.field.VarIntField;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTSerializable;
import de.atlasmc.registry.ProtocolRegistryValue;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.util.AtlasUtil;
import de.atlasmc.util.Builder;
import de.atlasmc.util.function.ObjBooleanConsumer;
import de.atlasmc.util.function.ObjFloatConsumer;
import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.function.ToFloatFunction;
import de.atlasmc.util.map.Multimap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

public class StreamCodecBuilder<T> implements Builder<StreamCodec<T>>{

	private List<StreamField<T>> fields = new ArrayList<>();
	private final Class<T> clazz;
	private Constructor<T> constructor;
	private Supplier<T> defaultConstructor;
	private ToBooleanFunction<T> optionalHas;
	
	public StreamCodecBuilder(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public Class<T> getType() {
		return clazz;
	}
	
	public StreamCodecBuilder<T> setConstructor(Constructor<T> constructor) {
		this.constructor = constructor;
		return this;
	}
	
	public Constructor<T> getConstructor() {
		return constructor;
	}
	
	public Supplier<T> getDefaultConstructor() {
		return defaultConstructor;
	}
	
	public List<StreamField<T>> getFields() {
		return fields;
	}
	
	public StreamCodecBuilder<T> addField(StreamField<T> field) {
		if (optionalHas != null) {
			field = new HasOptionalField<>(optionalHas, field);
			optionalHas = null;
		}
		fields.add(field);
		return this;
	}
	
	public <K extends Enum<K> & IDHolder> StreamCodecBuilder<T> enumVarIntConstructor(Class<K> clazz, Function<K, T> constructor, Function<T, K> keyReverseSupplier) {
		this.constructor = new EnumVarIntConstructor<>(clazz, constructor, keyReverseSupplier);
		return this;
	}
	
	public <K extends ProtocolRegistryValue> StreamCodecBuilder<T> registryVarIntConstructor(RegistryKey<K> registry, Function<K, T> constructor, Function<T, K> keyReverseSupplier) {
		this.constructor = new RegistryVarIntConstructor<>(registry, constructor, keyReverseSupplier);
		return this;
	}
	
	public StreamCodecBuilder<T> defaultConstructor(Supplier<T> defaultConstructor) {
		this.defaultConstructor = defaultConstructor;
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public StreamCodecBuilder<T> include(StreamCodec<? super T> include) {
		if (!(include instanceof StreamCodecImpl codec))
			throw new IllegalArgumentException("Codec must be instanceof StreamCodecImpl!");
		if (codec.getType().isAssignableFrom(clazz))
			throw new IllegalArgumentException("Codec type: " + codec.getType() + " is not assignable from type: " + clazz);
		codec.addToBuilder(this);
		return this;
	}
	
	public <V, E extends Enum<E> & IDHolder> StreamCodecBuilder<T> varIntEnumOrCodec(Function<T, V> get, BiConsumer<T, V> set, Class<E> clazz, StreamCodec<? extends V> codec) {
		return addField(new VarIntEnumOrCodecField<>(get, set, clazz, codec));
	}
	
	public <V extends ProtocolRegistryValue> StreamCodecBuilder<T> varIntRegistryOrCodec(Function<T, V> get, BiConsumer<T, V> set, RegistryKey<V> registry, StreamCodec<? extends V> codec) {
		return addField(new VarIntRegistryOrCodecField<>(get, set, registry, codec));
	}
	
	public <V extends Enum<V> & IDHolder> StreamCodecBuilder<T> varIntEnum(Function<T, V> get, BiConsumer<T, V> set, Class<V> clazz) {
		return addField(new VarIntEnumField<>(get, set, clazz));
	}
	
	public StreamCodecBuilder<T> varInt(ToIntFunction<T> get, ObjIntConsumer<T> set) {
		return addField(new VarIntField<>(get, set));
	}
	
	public StreamCodecBuilder<T> booleanValue(ToBooleanFunction<T> get, ObjBooleanConsumer<T> set) {
		return addField(new BooleanField<>(get, set));
	}
	
	public StreamCodecBuilder<T> staticBooleanValue(boolean value) {
		return addField(StaticBooleanField.valueOf(value));
	}
	
	public StreamCodecBuilder<T> namespacedKey(Function<T, NamespacedKey> get, BiConsumer<T, NamespacedKey> set) {
		return codec(get, set, NamespacedKey.STREAM_CODEC);
	}
	
	public StreamCodecBuilder<T> stringValue(Function<T, String> get, BiConsumer<T, String> set) {
		return addField(new CodecField<>(get, set, StringCodec.MAX_LENGTH_CODEC));
	}
	
	public StreamCodecBuilder<T> stringValue(Function<T, String> get, BiConsumer<T, String> set, int length) {
		return addField(new CodecField<>(get, set, length == StringCodec.MAX_LENGTH ? StringCodec.MAX_LENGTH_CODEC : new StringCodec(length)));
	}
	
	public StreamCodecBuilder<T> floatValue(ToFloatFunction<T> get, ObjFloatConsumer<T> set) {
		return addField(new FloatField<>(get, set));
	}
	
	public StreamCodecBuilder<T> longValue(ToLongFunction<T> get, ObjLongConsumer<T> set) {
		return addField(new LongField<>(get, set));
	}
	
	public StreamCodecBuilder<T> intValue(ToIntFunction<T> get, ObjIntConsumer<T> set) {
		return addField(new IntField<>(get, set));
	}
	
	public StreamCodecBuilder<T> doubleValue(ToDoubleFunction<T> get, ObjDoubleConsumer<T> set) {
		return addField(new DoubleField<>(get, set));
	}
	
	/**
	 * Makes the next field optional
	 * @param <V>
	 * @param has
	 * @return
	 */
	public <V> StreamCodecBuilder<T> optional(ToBooleanFunction<T> has) {
		this.optionalHas = has;
		return this;
	}
	
	public <V> StreamCodecBuilder<T> listCodec(ToBooleanFunction<T> has, Function<T, List<V>> get, StreamCodec<V> codec) {
		return addField(new CodecListField<>(has, get, codec));
	}
	
	public StreamCodecBuilder<T> stringList(ToBooleanFunction<T> has, Function<T, List<String>> get) {
		return addField(new CodecListField<>(has, get, StringCodec.MAX_LENGTH_CODEC));
	}
	
	public <V> StreamCodecBuilder<T> codec(Function<T, V> get, BiConsumer<T, V> set, StreamCodec<V> codec) {
		return addField(new CodecField<>(get, set, codec));
	}
	
	public <V> StreamCodecBuilder<T> codecCollection(ToBooleanFunction<T> has, Function<T, V> get, StreamCodec<V> codec) {
		return addField(new CodecCollectionField<>(has, get, codec));
	}
	
	public StreamCodecBuilder<T> codec(NBTCodec<T> codec) {
		return addField(new NBTCodecField<>(AtlasUtil.getSelf(), AtlasUtil.getSetVoid(), codec));
	}
	
	public <V extends NBTSerializable> StreamCodecBuilder<T> codec(Function<T, V> get, BiConsumer<T, V> set, NBTCodec<V> codec) {
		return addField(new NBTCodecField<>(get, set, codec));
	}
	
	/**
	 * Representation of a multimap as prefixed array where the first value of each value is used to resolve the key
	 * @param <K>
	 * @param <V>
	 * @param has
	 * @param get
	 * @param keySupplier
	 * @param codec
	 * @return
	 */
	public <K extends IDHolder, V> StreamCodecBuilder<T> multimapTypeToCodec(ToBooleanFunction<T> has, Function<T, Multimap<K, V>> get, IntFunction<K> keySupplier, StreamCodec<V> codec) {
		return addField(new MultimapTypeToCodecField<>(has, get, keySupplier, codec));
	}
	
	public <K extends IDHolder> StreamCodecBuilder<T> mapRegistryValueToVarInt(ToBooleanFunction<T> has, Function<T, Object2IntMap<K>> get, RegistryKey<K> registry) {
		return addField(new MapRegistryValueToVarIntField<>(has, get, registry));
	}

	@Override
	public StreamCodec<T> build() {
		return new StreamCodecImpl<>(this);
	}
	
}
