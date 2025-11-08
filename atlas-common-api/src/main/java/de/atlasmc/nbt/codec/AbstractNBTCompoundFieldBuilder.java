package de.atlasmc.nbt.codec;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.field.InnerCodecField;
import de.atlasmc.nbt.codec.field.NBTField;
import de.atlasmc.nbt.codec.field.NBTFieldBuilder;
import de.atlasmc.nbt.codec.field.CodecFieldBuilder;
import de.atlasmc.nbt.codec.field.CodecListFieldBuilder;
import de.atlasmc.nbt.codec.field.PrimitiveFieldBuilder;
import de.atlasmc.nbt.codec.field.CodecCollectionFieldBuilder;
import de.atlasmc.nbt.codec.field.CodecArraySearchByteIndexFieldBuilder;
import de.atlasmc.nbt.codec.field.TypeCollectionFieldBuilder;
import de.atlasmc.nbt.codec.field.TypeCollectionInnerSearchKeyFieldBuilder;
import de.atlasmc.nbt.codec.field.TypeListSearchIntIndexFieldBuilder;
import de.atlasmc.util.enums.EnumUtil;
import de.atlasmc.util.function.ObjBooleanConsumer;
import de.atlasmc.util.function.ObjByteConsumer;
import de.atlasmc.util.function.ObjFloatConsumer;
import de.atlasmc.util.function.ObjShortConsumer;
import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.function.ToFloatFunction;
import de.atlasmc.util.map.Multimap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

public interface AbstractNBTCompoundFieldBuilder<T, B extends AbstractNBTCompoundFieldBuilder<T, B>> {
	
	public static final int TYPE_COUNT = EnumUtil.getValues(TagType.class).size() - 1; // tag end (0) is not used
	
	B addField(NBTField<T> field);
	
	default B addField(NBTFieldBuilder<T, ?> builder) {
		return addField(builder.build());
	}
	
	default B intField(CharSequence key, ToIntFunction<T> get, ObjIntConsumer<T> set) {
		return addField(primitiveBuilder(key, TagType.INT, get, set).setDefaultValue(0));
	}
	
	default B intField(CharSequence key, ToIntFunction<T> get, ObjIntConsumer<T> set, int defaultValue) {
		return addField(primitiveBuilder(key, TagType.INT, get, set).setDefaultValue(defaultValue));
	}
	
	default B byteField(CharSequence key, ToIntFunction<T> get, ObjByteConsumer<T> set) {
		return addField(primitiveBuilder(key, TagType.BYTE, get, set).setDefaultValue(0));
	}
	
	default B byteField(CharSequence key, ToIntFunction<T> get, ObjByteConsumer<T> set, byte defaultValue) {
		return addField(primitiveBuilder(key, TagType.BYTE, get, set).setDefaultValue(defaultValue));
	}
	
	default B longField(CharSequence key, ToLongFunction<T> get, ObjLongConsumer<T> set) {
		return addField(primitiveBuilder(key, TagType.LONG, get, set).setDefaultValue(0));
	}
	
	default B longField(CharSequence key, ToLongFunction<T> get, ObjLongConsumer<T> set, long defaultValue) {
		return addField(primitiveBuilder(key, TagType.LONG, get, set).setDefaultValue(defaultValue));
	}
	
	default B shortField(CharSequence key, ToIntFunction<T> get, ObjShortConsumer<T> set) {
		return addField(primitiveBuilder(key, TagType.SHORT, get, set).setDefaultValue(0));
	}
	
	default B shortField(CharSequence key, ToIntFunction<T> get, ObjShortConsumer<T> set, short defaultValue) {
		return addField(primitiveBuilder(key, TagType.SHORT, get, set).setDefaultValue(defaultValue));
	}
	
	default B doubleField(CharSequence key, ToDoubleFunction<T> get, ObjDoubleConsumer<T> set) {
		return addField(primitiveBuilder(key, TagType.DOUBLE, get, set).setDefaultValue(0));
	}
	
	default B doubleField(CharSequence key, ToDoubleFunction<T> get, ObjDoubleConsumer<T> set, double defaultValue) {
		return addField(primitiveBuilder(key, TagType.DOUBLE, get, set).setDefaultValue(defaultValue));
	}
	
	default B floatField(CharSequence key, ToFloatFunction<T> get, ObjFloatConsumer<T> set) {
		return addField(primitiveBuilder(key, TagType.FLOAT, get, set).setDefaultValue(0));
	}
	
	default B floatField(CharSequence key, ToFloatFunction<T> get, ObjFloatConsumer<T> set, float defaultValue) {
		return addField(primitiveBuilder(key, TagType.FLOAT, get, set).setDefaultValue(defaultValue));
	}
	
	default B boolField(CharSequence key, ToBooleanFunction<T> get, ObjBooleanConsumer<T> set) {
		return addField(primitiveBuilder(key, null, get, set).setDefaultValue(false));
	}
	
	default B boolField(CharSequence key, ToBooleanFunction<T> get, ObjBooleanConsumer<T> set, boolean defaultValue) {
		return addField(primitiveBuilder(key, null, get, set).setDefaultValue(defaultValue));
	}
	
	default <V> B codecCollection(CharSequence key, ToBooleanFunction<T> has, Function<T, V> get, NBTCodec<V> codec) {
		return addField(codecCollectionBuilder(key, has, get, codec));
	}
	
	default <V> B codec(CharSequence key, Function<T, V> get, BiConsumer<T, V> set, NBTCodec<? extends V> codec) {
		return addField(objectBuilder(key, get, set, codec));
	}
	
	default <V> B codec(CharSequence key, Function<T, V> get, BiConsumer<T, V> set, NBTCodec<? extends V> codec, V defaultValue) {
		return addField(objectBuilder(key, get, set, codec).setDefaultValue(defaultValue));
	}
	
	default <V> B innerTypeCompoundField(CharSequence key, Function<T, ? super V> get, NBTCodec<V> codec) {
		return addField(new InnerCodecField<>(key, get, codec, false));
	}
	
	default <V extends Namespaced> B mapNamespacedToInt(CharSequence key, ToBooleanFunction<T> has, Function<T, Object2IntMap<V>> get, Function<String, V> keySupplier) {
		return addField(codecCollectionBuilder(key, has, get, new MapNamespacedToInt<>(keySupplier)));
	}
	
	default <V> B codecList(CharSequence key, ToBooleanFunction<T> has, Function<T, List<V>> getList, NBTCodec<V> handler) {
		return codecList(key, has, getList, handler, true);
	}
	
	default <V> B codecList(CharSequence key, ToBooleanFunction<T> has, Function<T, List<V>> get, NBTCodec<V> codec, boolean optional) {
		return addField(new CodecListFieldBuilder<T, V>().setKey(key).setGetter(get).setHasData(has).setOptional(optional).setFieldType(codec));
	}
	
	default <K, V> B mapTypeToCodec(CharSequence key, ToBooleanFunction<T> has, Function<T, Map<K, V>> get, NBTCodec<V> codec, Function<V, K> getKey) {
		return addField(codecCollectionBuilder(key, has, get, new MapTypeToCodec<>(codec, getKey)));
	}
	
	default <K extends Namespaced, V> B multimapTypeToCodec(CharSequence key, ToBooleanFunction<T> has, Function<T, Multimap<K, V>> get, NBTCodec<V> codec, CharSequence keyField, Function<NamespacedKey, K> keySupplier) {
		return addField(codecCollectionBuilder(key, has, get, new MultimapTypeToCodec<>(keyField, keySupplier, codec)));
	}
	
	default <V> B mapFieldNameToCodec(CharSequence key, ToBooleanFunction<T> has, Function<T, Map<String, V>> get, NBTCodec<V> codec) {
		return addField(codecCollectionBuilder(key, has, get, new MapFieldNameToCodec<>(codec)));
	}
	
	default <V> B typeCollection(CharSequence key, ToBooleanFunction<T> has, Function<T, Collection<V>> get, BiConsumer<T, V> set, NBTCodec<V> codec) {
		return addField(new TypeCollectionFieldBuilder<T, V>().setKey(key).setHasData(has).setGetter(get).setSetter(set).setFieldType(codec));
	}
	
	default <K extends NBTSerializable, C extends Namespaced> B typeCollectionInnerSearchKey(CharSequence key, ToBooleanFunction<T> has, Function<T, Collection<K>> get, CharSequence keyField, Function<NamespacedKey, C> keySupplier, BiFunction<T, C, K> constructor, Function<K, C> keyReverse) {
		return addField(new TypeCollectionInnerSearchKeyFieldBuilder<T, K, C>()
				.setKey(key)
				.setHasData(has)
				.setGetter(get)
				.setKeyField(keyField)
				.setKeySupplier(keySupplier)
				.setFieldType(constructor)
				.setKeyReverse(keyReverse));
	}
	
	default <V> B typeListSearchIntIndexField(CharSequence key, CharSequence indexKey, ToBooleanFunction<T> has, Function<T, Int2ObjectMap<V>> get, NBTCodec<V> codec) {
		return addField(new TypeListSearchIntIndexFieldBuilder<T, V>().setKey(key).setHasData(has).setGetter(get).setFieldType(codec).setIndexKey(indexKey));
	}
	
	default <V> B codecArraySearchByteIndexField(CharSequence key, CharSequence indexKey, ToBooleanFunction<T> has, Function<T, V[]> get, NBTCodec<V> codec) {
		return addField(new CodecArraySearchByteIndexFieldBuilder<T, V>().setKey(key).setIndexKey(indexKey).setHasData(has).setGetter(get).setFieldType(codec));
	}
	
	default <V> CodecFieldBuilder<T, V> objectBuilder(CharSequence key, Function<T, V> get, BiConsumer<T, V> set, NBTCodec<? extends V> codec) {
		return new CodecFieldBuilder<T, V>().setKey(key).setGetter(get).setSetter(set).setCodec(codec);
	}
	
	default <G, S> PrimitiveFieldBuilder<T, G, S> primitiveBuilder(CharSequence key, TagType type, G get, S set) {
		return new PrimitiveFieldBuilder<T, G, S>().setKey(key).setType(type).setGetter(get).setSetter(set);
	}
	
	default <V> CodecCollectionFieldBuilder<T, V> codecCollectionBuilder(CharSequence key, ToBooleanFunction<T> has, Function<T, V> get, NBTCodec<V> type) {
		return new CodecCollectionFieldBuilder<T, V>().setKey(key).setHasData(has).setGetter(get).setFieldType(type);
	}
	
	B getThis();

}
