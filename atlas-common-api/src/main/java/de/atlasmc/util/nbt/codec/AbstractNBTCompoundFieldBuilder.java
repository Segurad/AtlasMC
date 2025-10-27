package de.atlasmc.util.nbt.codec;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import org.joml.Quaternionf;
import org.joml.Vector3d;
import org.joml.Vector3f;
import org.joml.Vector3i;

import de.atlasmc.Color;
import de.atlasmc.ColorValue;
import de.atlasmc.IDHolder;
import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.registry.RegistryKey;
import de.atlasmc.tag.TagKey;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.EnumUtil;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.function.ObjBooleanConsumer;
import de.atlasmc.util.function.ObjByteConsumer;
import de.atlasmc.util.function.ObjFloatConsumer;
import de.atlasmc.util.function.ObjShortConsumer;
import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.function.ToFloatFunction;
import de.atlasmc.util.map.Multimap;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.codec.field.InnerTypeField;
import de.atlasmc.util.nbt.codec.field.NBTField;
import de.atlasmc.util.nbt.codec.field.NBTFieldBuilder;
import de.atlasmc.util.nbt.codec.field.ObjectFieldBuilder;
import de.atlasmc.util.nbt.codec.field.ObjectListFieldBuilder;
import de.atlasmc.util.nbt.codec.field.PrimitiveFieldBuilder;
import de.atlasmc.util.nbt.codec.field.ReuseableObjectFieldBuilder;
import de.atlasmc.util.nbt.codec.field.TypeArraySearchByteIndexFieldBuilder;
import de.atlasmc.util.nbt.codec.field.TypeCollectionFieldBuilder;
import de.atlasmc.util.nbt.codec.field.TypeCollectionInnerSearchKeyFieldBuilder;
import de.atlasmc.util.nbt.codec.field.TypeListSearchIntIndexFieldBuilder;
import de.atlasmc.util.nbt.codec.type.*;
import de.atlasmc.util.nbt.tag.NBT;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.IntCollection;
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
	
	default B intNullableField(CharSequence key, Function<T, Integer> get, BiConsumer<T, Integer> set, Integer defaultValue) {
		return addField(objectBuilder(key, get, set, IntObjectType.getInstance()).setDefaultValue(defaultValue));
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
	
	default B longNullableField(CharSequence key, Function<T, Long> get, BiConsumer<T, Long> set) {
		return addField(objectBuilder(key, get, set, LongObjectType.getInstance()));
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
	
	default B string(CharSequence key, Function<T, String> get, BiConsumer<T, String> set) {
		return addField(objectBuilder(key, get, set, StringType.getInstance()));
	}
	
	default <V extends Namespaced> B registryValue(CharSequence key, Function<T, V> get, BiConsumer<T, V> set, RegistryKey<V> registry) {
		return addField(objectBuilder(key, get, set, new RegistryValueType<>(registry)));
	}
	
	default <V extends Namespaced> B registryValueList(CharSequence key, ToBooleanFunction<T> has, Function<T, List<V>> get, RegistryKey<V> registry) {
		return addField(new ObjectListFieldBuilder<T, V>().setKey(key).setGetter(get).setHasData(has).setFieldType(new RegistryValueType<>(registry)));
	}
	
	default <V extends Namespaced & NBTSerializable> B registryValue(CharSequence key, Function<T, V> get, BiConsumer<T, V> set, RegistryKey<V> registry, NBTCodec<V> codec) {
		registryValue(key, get, set, registry);
		return typeCompoundField(key, get, set, codec);
	}
	
	default B namespacedKey(CharSequence key, Function<T, NamespacedKey> get, BiConsumer<T, NamespacedKey> set) {
		return addField(objectBuilder(key, get, set, NamespacedKeyType.getInstance()));
	}
	
	@SuppressWarnings("unchecked")
	default <V> B typeCompoundField(CharSequence key, Function<T, ? super V> get, BiConsumer<T, ? super V> set, NBTCodec<V> codec) {
		return addField(objectBuilder(key, (Function<T, Object>) get, (BiConsumer<T, Object>) set, new CodecType<>(codec)));
	}
	
	default <V> B innerTypeCompoundField(CharSequence key, Function<T, ? super V> get, NBTCodec<V> codec) {
		return addField(new InnerTypeField<>(key, get, codec, false));
	}
	
	default <V extends Namespaced> B compoundMapNamespaced2Int(CharSequence key, ToBooleanFunction<T> has, Function<T, Object2IntMap<V>> get, Function<String, V> keySupplier) {
		return addField(reuseableObjectBuilder(key, has, get, new MapNamespaced2Int<>(keySupplier)));
	}
	
	default <V extends Namespaced> B compoundMapNamespacedType(CharSequence key, ToBooleanFunction<T> has, Function<T, Map<NamespacedKey, V>> getMap, NBTCodec<V> codec) {
		return compoundMapType2Type(key, has, getMap, codec, Namespaced.getKeySupplier());
	}
	
	default <V> B compoundMapString2Type(CharSequence key, ToBooleanFunction<T> has, Function<T, Map<String, V>> get, NBTCodec<V> codec) {
		return addField(reuseableObjectBuilder(key, has, get, new MapString2TypeField<>(codec)));
	}
	
	default <K extends Namespaced, V> B multimapType2TypeList(CharSequence key, ToBooleanFunction<T> has, Function<T, Multimap<K, V>> get, CharSequence keyField, Function<NamespacedKey, K> keySupplier, NBTCodec<V> codec) {
		return addField(reuseableObjectBuilder(key, has, get, new MultimapType2TypeList<>(keyField, keySupplier, codec)));
	}
	
	default <K, V> B compoundMapType2Type(CharSequence key, ToBooleanFunction<T> has, Function<T, Map<K, V>> get, NBTCodec<V> codec, Function<V, K> getKey) {
		return addField(reuseableObjectBuilder(key, has, get, new MapType2Type<>(codec, getKey)));
	}
	
	default B color(CharSequence key, Function<T, Color> get, BiConsumer<T, Color> set, Color defaultValue) {
		return addField(objectBuilder(key, get, set, ColorType.getInstance()).setDefaultValue(defaultValue));
	}
	
	default B colorValue(CharSequence key, Function<T, ColorValue> get, BiConsumer<T, ColorValue> set) {
		return addField(objectBuilder(key, get, set, ColorValueType.getInstance()));
	}
	
	default <K> B stringToObject(CharSequence key, Function<T, K> get, BiConsumer<T, K> set, Function<String, K> stringToObject, Function<K, String> objectToString) {
		return addField(objectBuilder(key, get, set, new StringToObjectType<>(stringToObject, objectToString)));
	}
	
	default <K extends Enum<K> & EnumName> B enumStringField(CharSequence key, Function<T, K> get, BiConsumer<T, K> set, Class<K> clazz, K defaultValue) {
		return addField(objectBuilder(key, get, set, new EnumStringType<>(clazz)).setDefaultValue(defaultValue));
	}
	
	default <K, E extends Enum<E> & EnumName> B enumStringOrType(CharSequence key, Function<T, K> get, BiConsumer<T, K> set, Class<E> clazz, NBTCodec<? extends K> codec) {
		return enumStringOrType(key, get, set, clazz, codec, null);
	}
	
	default <K, E extends Enum<E> & EnumName> B enumStringOrType(CharSequence key, Function<T, K> get, BiConsumer<T, K> set, Class<E> clazz, NBTCodec<? extends K> codec, K defaultValue) {
		addField(objectBuilder(key, get, set, new EnumStringType<>(clazz)).setDefaultValue(defaultValue));
		return typeCompoundField(key, get, set, codec);
	}
	
	default <V> B objectByteField(CharSequence key, Function<T, V> get, BiConsumer<T, V> set, IntFunction<V> toObject, ToIntFunction<V> toByte, V defaultValue) {
		return addField(objectBuilder(key, get, set, new ByteToObjectType<>(toObject, toByte)).setDefaultValue(defaultValue));
	}
	
	default <K extends Enum<K> & IDHolder> B enumByteField(CharSequence key, Function<T, K> get, BiConsumer<T, K> set, Class<K> clazz, K defaultValue) {
		return addField(objectBuilder(key, get, set, new EnumByteType<>(clazz)).setDefaultValue(defaultValue));
	}
	
	default <K extends Enum<K> & IDHolder> B enumIntField(CharSequence key, Function<T, K> get, BiConsumer<T, K> set, Class<K> clazz, K defaultValue) {
		return addField(objectBuilder(key, get, set, new EnumIntType<>(clazz)).setDefaultValue(defaultValue));
	}
	
	@SuppressWarnings("unchecked")
	default <K extends Enum<K> & EnumName> B interfacedEnumStringField(CharSequence key, Function<T, ? super K> get, BiConsumer<T, ? super K> set, Class<K> clazz, K defaultValue) {
		return addField(objectBuilder(key, (Function<T, Object>) get, (BiConsumer<T, Object>) set, new EnumStringType<Object, K>(clazz)));
	}
	
	default B chat(CharSequence key, Function<T, Chat> get, BiConsumer<T, Chat> set) {
		typeCompoundField(key, get, set, ChatComponent.NBT_CODEC);			
		return stringToObject(key, get, set, NBTCodecBuilder.stringToChat, NBTCodecBuilder.chatToString);
		// list format will be ignored
	}
	
	default B chatList(CharSequence key, ToBooleanFunction<T> has, Function<T, List<Chat>> get) {
		return addField(reuseableObjectBuilder(key, has, get, ChatListType.getInstance()));
	}
	
	default B rawField(CharSequence key, List<TagType> types, Function<T, NBT> get, BiConsumer<T, NBT> set) {
		return addField(objectBuilder(key, get, set, new RawType(types)));
	}
	
	default B uuid(CharSequence key, Function<T, UUID> get, BiConsumer<T, UUID> set) {
		return addField(objectBuilder(key, get, set, UUIDType.getInstance()));
	}
	
	default B uuidList(CharSequence key, ToBooleanFunction<T> has, Function<T, List<UUID>> get, boolean optional) {
		return addField(new ObjectListFieldBuilder<T, UUID>().setKey(key).setGetter(get).setHasData(has).setOptional(optional).setFieldType(UUIDType.getInstance()));
	}
	
	default B intArray(CharSequence key, Function<T, int[]> get, BiConsumer<T, int[]> set) {
		return addField(objectBuilder(key, get, set, IntArrayType.getInstance()));
	}
	

	default <V> B typeList(CharSequence key, ToBooleanFunction<T> has, Function<T, List<V>> getList, NBTCodec<V> handler) {
		return typeList(key, has, getList, handler, true);
	}
	
	default <V> B typeList(CharSequence key, ToBooleanFunction<T> has, Function<T, List<V>> get, NBTCodec<V> codec, boolean optional) {
		return addField(new ObjectListFieldBuilder<T, V>().setKey(key).setGetter(get).setHasData(has).setOptional(optional).setFieldType(new CodecType<>(codec)));
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
	
	default <V> B typeArraySearchByteIndexField(CharSequence key, CharSequence indexKey, ToBooleanFunction<T> has, Function<T, V[]> get, NBTCodec<V> codec) {
		return addField(new TypeArraySearchByteIndexFieldBuilder<T, V>().setKey(key).setIndexKey(indexKey).setHasData(has).setGetter(get).setFieldType(codec));
	}
	
	default <V extends Namespaced> B dataSetField(CharSequence key, Function<T, DataSet<V>> get, BiConsumer<T, DataSet<V>> set, RegistryKey<V> registry) {
		return addField(objectBuilder(key, get, set, new DataSetType<>(registry)));
	}
	
	default <V> B tagField(CharSequence key, Function<T, TagKey<V>> get, BiConsumer<T, TagKey<V>> set) {
		return addField(objectBuilder(key, get, set, TagKeyType.getInstance()));
	}
	
	default B floatListField(CharSequence key, ToBooleanFunction<T> has, Function<T, FloatList> get) {
		return addField(new ReuseableObjectFieldBuilder<T, FloatList>().setKey(key).setHasData(has).setGetter(get).setFieldType(FloatListType.getInstance()));
	}
	
	default B byteArrayField(CharSequence key, Function<T, byte[]> get, BiConsumer<T, byte[]> set) {
		return addField(objectBuilder(key, get, set, ByteArrayType.getInstance()));
	}
	
	default B stringListField(CharSequence key, ToBooleanFunction<T> has, Function<T, List<String>> get) {
		return addField(new ObjectListFieldBuilder<T, String>().setKey(key).setHasData(has).setGetter(get).setFieldType(StringType.getInstance()));
	}
	
	default B namespacedKeyListField(CharSequence key, ToBooleanFunction<T> has, Function<T, List<NamespacedKey>> get) {
		return addField(new ObjectListFieldBuilder<T, NamespacedKey>().setKey(key).setHasData(has).setGetter(get).setFieldType(NamespacedKeyType.getInstance()));
	}
	
	default B booleanListField(CharSequence key, ToBooleanFunction<T> has, Function<T, BooleanList> get) {
		return addField(reuseableObjectBuilder(key, has, get, BooleanListType.getInstance()));
	}
	
	default B intListField(CharSequence key, ToBooleanFunction<T> has, Function<T, IntCollection> get) {
		return addField(reuseableObjectBuilder(key, has, get, IntCollectionType.getInstance()));
	}
	
	default B vector3f(CharSequence key, Function<T, Vector3f> get, BiConsumer<T, Vector3f> set) {
		return addField(objectBuilder(key, get, set, Vector3fType.getInstance()).setReuseValue(true));
	}
	
	default B vector3i(CharSequence key, Function<T, Vector3i> get, BiConsumer<T, Vector3i> set) {
		return addField(objectBuilder(key, get, set, Vector3iType.getInstance()).setReuseValue(true));
	}
	
	default B vector3d(CharSequence key, Function<T, Vector3d> get, BiConsumer<T, Vector3d> set) {
		return addField(objectBuilder(key, get, set, Vector3dType.getInstance()).setReuseValue(true));
	}
	
	default B quaternionf(CharSequence key, Function<T, Quaternionf> get, BiConsumer<T, Quaternionf> set) {
		return addField(objectBuilder(key, get, set, QuaternionfType.getInstance()).setReuseValue(true));
	}
	
	default <V> ObjectFieldBuilder<T, V> objectBuilder(CharSequence key, Function<T, V> get, BiConsumer<T, V> set, ObjectType<V> type) {
		return new ObjectFieldBuilder<T, V>().setKey(key).setGetter(get).setSetter(set).setFieldType(type);
	}
	
	default <G, S> PrimitiveFieldBuilder<T, G, S> primitiveBuilder(CharSequence key, TagType type, G get, S set) {
		return new PrimitiveFieldBuilder<T, G, S>().setKey(key).setType(type).setGetter(get).setSetter(set);
	}
	
	default <V> ReuseableObjectFieldBuilder<T, V> reuseableObjectBuilder(CharSequence key, ToBooleanFunction<T> has, Function<T, V> get, ObjectType<V> type) {
		return new ReuseableObjectFieldBuilder<T, V>().setKey(key).setHasData(has).setGetter(get).setFieldType(type);
	}
	
	B getThis();

}
