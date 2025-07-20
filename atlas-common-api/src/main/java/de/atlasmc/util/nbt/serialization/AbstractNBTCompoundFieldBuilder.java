package de.atlasmc.util.nbt.serialization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
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

import de.atlasmc.Color;
import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.registry.Registry;
import de.atlasmc.tag.Tag;
import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.function.ObjBooleanConsumer;
import de.atlasmc.util.function.ObjByteConsumer;
import de.atlasmc.util.function.ObjFloatConsumer;
import de.atlasmc.util.function.ObjShortConsumer;
import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.function.ToFloatFunction;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.serialization.fields.BooleanField;
import de.atlasmc.util.nbt.serialization.fields.BooleanListField;
import de.atlasmc.util.nbt.serialization.fields.ByteArrayField;
import de.atlasmc.util.nbt.serialization.fields.ByteField;
import de.atlasmc.util.nbt.serialization.fields.ChatColorColorField;
import de.atlasmc.util.nbt.serialization.fields.ChatListField;
import de.atlasmc.util.nbt.serialization.fields.ColorField;
import de.atlasmc.util.nbt.serialization.fields.CompoundTypeField;
import de.atlasmc.util.nbt.serialization.fields.DataSetField;
import de.atlasmc.util.nbt.serialization.fields.DoubleField;
import de.atlasmc.util.nbt.serialization.fields.EnumByteField;
import de.atlasmc.util.nbt.serialization.fields.EnumIntField;
import de.atlasmc.util.nbt.serialization.fields.EnumStringField;
import de.atlasmc.util.nbt.serialization.fields.FloatField;
import de.atlasmc.util.nbt.serialization.fields.FloatListField;
import de.atlasmc.util.nbt.serialization.fields.IntArrayField;
import de.atlasmc.util.nbt.serialization.fields.IntField;
import de.atlasmc.util.nbt.serialization.fields.IntListField;
import de.atlasmc.util.nbt.serialization.fields.IntNullableField;
import de.atlasmc.util.nbt.serialization.fields.InterfacedEnumStringField;
import de.atlasmc.util.nbt.serialization.fields.LongField;
import de.atlasmc.util.nbt.serialization.fields.MapNamespaced2Int;
import de.atlasmc.util.nbt.serialization.fields.MapNamespacedType;
import de.atlasmc.util.nbt.serialization.fields.NBTField;
import de.atlasmc.util.nbt.serialization.fields.NamespacedKeyField;
import de.atlasmc.util.nbt.serialization.fields.QuaternionfField;
import de.atlasmc.util.nbt.serialization.fields.RawField;
import de.atlasmc.util.nbt.serialization.fields.RegistryValueField;
import de.atlasmc.util.nbt.serialization.fields.ShortField;
import de.atlasmc.util.nbt.serialization.fields.StringField;
import de.atlasmc.util.nbt.serialization.fields.StringListField;
import de.atlasmc.util.nbt.serialization.fields.StringToObjectField;
import de.atlasmc.util.nbt.serialization.fields.TagField;
import de.atlasmc.util.nbt.serialization.fields.TypeArraySearchByteIndexField;
import de.atlasmc.util.nbt.serialization.fields.TypeListField;
import de.atlasmc.util.nbt.serialization.fields.TypeListSearchIntIndexField;
import de.atlasmc.util.nbt.serialization.fields.UUIDField;
import de.atlasmc.util.nbt.serialization.fields.Vector3dField;
import de.atlasmc.util.nbt.serialization.fields.Vector3fField;
import de.atlasmc.util.nbt.tag.NBT;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.floats.FloatList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

public abstract class AbstractNBTCompoundFieldBuilder<T, B extends AbstractNBTCompoundFieldBuilder<T, B>> {
	
	private static final int TYPE_COUNT = TagType.getValues().size() - 1; // tag end (0) is not used
	
	private List<Map<CharKey, NBTField<T>>> typeFields = new ArrayList<>(TYPE_COUNT);
	
	private static final Function<Chat, String> chatToString = Chat::toText;
	private static final Function<String, Chat> stringToChat = ChatUtil::toChat;
	
	public B addField(NBTField<T> field) {
		for (TagType type : field.types) {
			final int id = type.getID() - 1; // tag end (0) is not used 
			Map<CharKey, NBTField<T>> fields = typeFields.get(id);
			if (fields == null)
				typeFields.set(id, fields = new HashMap<>());
			if (field.key == null)
				throw new NBTException("Key of field can not be null!");
			fields.put(field.key, field);
		}
		return getThis();
	}
	
	public B intField(CharSequence key, ToIntFunction<T> get, ObjIntConsumer<T> set) {
		return addField(new IntField<>(key, get, set, false, 0));
	}
	
	public B intField(CharSequence key, ToIntFunction<T> get, ObjIntConsumer<T> set, int defaultValue) {
		return addField(new IntField<>(key, get, set, true, defaultValue));
	}
	
	public B intNullableField(CharSequence key, Function<T, Integer> get, BiConsumer<T, Integer> set, Integer defaultValue) {
		return addField(new IntNullableField<>(key, get, set, true, defaultValue));
	}
	
	public B byteField(CharSequence key, ToIntFunction<T> get, ObjByteConsumer<T> set) {
		return addField(new ByteField<>(key, get, set, false, (byte) 0));
	}
	
	public B byteField(CharSequence key, ToIntFunction<T> get, ObjByteConsumer<T> set, byte defaultValue) {
		return addField(new ByteField<>(key, get, set, true, defaultValue));
	}
	
	public B longField(CharSequence key, ToLongFunction<T> get, ObjLongConsumer<T> set) {
		return addField(new LongField<>(key, get, set, false, 0));
	}
	
	public B longField(CharSequence key, ToLongFunction<T> get, ObjLongConsumer<T> set, long defaultValue) {
		return addField(new LongField<>(key, get, set, true, defaultValue));
	}
	
	public B shortField(CharSequence key, ToIntFunction<T> get, ObjShortConsumer<T> set) {
		return addField(new ShortField<>(key, get, set, false, (short) 0));
	}
	
	public B shortField(CharSequence key, ToIntFunction<T> get, ObjShortConsumer<T> set, short defaultValue) {
		return addField(new ShortField<>(key, get, set, true, defaultValue));
	}
	
	public B doubleField(CharSequence key, ToDoubleFunction<T> get, ObjDoubleConsumer<T> set) {
		return addField(new DoubleField<>(key, get, set, false, 0));
	}
	
	public B doubleField(CharSequence key, ToDoubleFunction<T> get, ObjDoubleConsumer<T> set, double defaultValue) {
		return addField(new DoubleField<>(key, get, set, true, defaultValue));
	}
	
	public B floatField(CharSequence key, ToFloatFunction<T> get, ObjFloatConsumer<T> set) {
		return addField(new FloatField<>(key, get, set, false, 0));
	}
	
	public B floatField(CharSequence key, ToFloatFunction<T> get, ObjFloatConsumer<T> set, float defaultValue) {
		return addField(new FloatField<>(key, get, set, true, defaultValue));
	}
	
	public B boolField(CharSequence key, ToBooleanFunction<T> get, ObjBooleanConsumer<T> set) {
		return addField(new BooleanField<>(key, get, set, false, false));
	}
	
	public B boolField(CharSequence key, ToBooleanFunction<T> get, ObjBooleanConsumer<T> set, boolean defaultValue) {
		return addField(new BooleanField<>(key, get, set, true, defaultValue));
	}
	
	public B string(CharSequence key, Function<T, String> get, BiConsumer<T, String> set) {
		return addField(new StringField<>(key, get, set));
	}
	
	public <K extends Namespaced> B registryValue(CharSequence key, Function<T, K> get, BiConsumer<T, K> set, Registry<K> registry) {
		return addField(new RegistryValueField<>(key, get, set, registry));
	}
	
	public <K extends Namespaced & NBTSerializable> B registryValue(CharSequence key, Function<T, K> get, BiConsumer<T, K> set, Registry<K> registry, NBTSerializationHandler<K> handler) {
		registryValue(key, get, set, registry);
		return typeComponentField(key, get, set, handler);
	}
	
	public B namespacedKey(CharSequence key, Function<T, NamespacedKey> get, BiConsumer<T, NamespacedKey> set) {
		return addField(new NamespacedKeyField<>(key, get, set));
	}
	
	public <K> B typeComponentField(CharSequence key, Function<T, ? super K> get, BiConsumer<T, ? super K> set, NBTSerializationHandler<K> handler) {
		return addField(new CompoundTypeField<>(key, get, set, handler));
	}
	
	public <K extends Namespaced> B compoundMapNamespaced2Int(CharSequence key, ToBooleanFunction<T> has, Function<T, Object2IntMap<K>> getMap, Function<String, K> keySupplier) {
		return addField(new MapNamespaced2Int<>(key, has, getMap, keySupplier));
	}
	
	public <K extends Namespaced> B compoundMapNamespacedType(CharSequence key, ToBooleanFunction<T> has, Function<T, Map<NamespacedKey, K>> getMap, NBTSerializationHandler<K> handler) {
		return addField(new MapNamespacedType<>(key, has, getMap, handler));
	}
	
	public B color(CharSequence key, Function<T, Color> get, BiConsumer<T, Color> set) {
		return addField(new ColorField<>(key, get, set, false, 0));
	}
	
	public B color(CharSequence key, Function<T, Color> get, BiConsumer<T, Color> set, int defaultValue) {
		return addField(new ColorField<>(key, get, set, true, defaultValue));
	}
	
	public B chatColorColor(CharSequence key, Function<T, ChatColor> get, BiConsumer<T, ChatColor> set, Function<T, Color> getColor, BiConsumer<T, Color> setColor) {
		return addField(new ChatColorColorField<>(key, get, set, getColor, setColor));
	}
	
	public <K> B stringToObject(CharSequence key, Function<T, K> get, BiConsumer<T, K> set, Function<String, K> stringToObject, Function<K, String> objectToString) {
		return addField(new StringToObjectField<>(key, get, set, stringToObject, objectToString));
	}
	
	public <K extends Enum<?> & EnumName> B enumStringField(CharSequence key, Function<T, K> get, BiConsumer<T, K> set, Function<String, K> enumSupplier, K defaultValue) {
		return addField(new EnumStringField<>(key, get, set, enumSupplier, defaultValue));
	}
	
	public <K extends Enum<?>> B enumByteField(CharSequence key, Function<T, K> get, BiConsumer<T, K> set, IntFunction<K> enumSupplier, ToIntFunction<K> idSupplier, K defaultValue) {
		return addField(new EnumByteField<>(key, get, set, enumSupplier, idSupplier, defaultValue));
	}
	
	public <K extends Enum<?> & EnumID> B enumIntField(CharSequence key, Function<T, K> get, BiConsumer<T, K> set, IntFunction<K> enumSupplier, K defaultValue) {
		return addField(new EnumIntField<>(key, get, set, enumSupplier, defaultValue));
	}
	
	public <K extends Enum<?> & EnumName> B interfacedEnumStringField(CharSequence key, Function<T, ? super K> get, BiConsumer<T, ? super K> set, Function<String, K> enumSupplier, K defaultValue) {
		return addField(new InterfacedEnumStringField<>(key, get, set, enumSupplier, defaultValue));
	}
	
	public B chat(CharSequence key, Function<T, Chat> get, BiConsumer<T, Chat> set) {
		typeComponentField(key, get, set, ChatComponent.NBT_HANDLER);			
		return stringToObject(key, get, set, stringToChat, chatToString);
		// list format will be ignored
	}
	
	public B chatList(CharSequence key, ToBooleanFunction<T> has, Function<T, List<Chat>> get, boolean optional) {
		return addField(new ChatListField<>(key, has, get, optional));
	}
	
	public B rawField(CharSequence key, List<TagType> types, Function<T, NBT> get, BiConsumer<T, NBT> set, boolean includeKey) {
		return addField(new RawField<>(key, types, get, set, includeKey));
	}
	
	public B uuid(CharSequence key, Function<T, UUID> get, BiConsumer<T, UUID> set) {
		return addField(new UUIDField<>(key, get, set));
	}
	
	public B intArray(CharSequence key, Function<T, int[]> get, BiConsumer<T, int[]> set) {
		return addField(new IntArrayField<>(key, get, set));
	}
	

	public <K> B typeList(CharSequence key, ToBooleanFunction<T> has, Function<T, List<K>> getList, NBTSerializationHandler<K> handler) {
		return typeList(key, has, getList, handler, true);
	}
	
	public <K> B typeList(CharSequence key, ToBooleanFunction<T> has, Function<T, List<K>> getList, NBTSerializationHandler<K> handler, boolean optional) {
		return addField(new TypeListField<>(key, has, getList, handler, optional));
	}
	
	public <K> B typeListSearchIntIndexField(CharSequence key, CharSequence indexKey, ToBooleanFunction<T> has, Function<T, List<K>> get, NBTSerializationHandler<K> handler, boolean optional) {
		return addField(new TypeListSearchIntIndexField<>(key, indexKey, has, get, handler, optional));
	}
	
	public <K> B typeArraySearchByteIndexField(CharSequence key, CharSequence indexKey, ToBooleanFunction<T> has, Function<T, K[]> getArray, NBTSerializationHandler<K> handler) {
		return addField(new TypeArraySearchByteIndexField<>(key, indexKey, has, getArray, handler));
	}
	
	public <K extends Namespaced> B dataSetField(CharSequence key, Function<T, DataSet<K>> get, BiConsumer<T, DataSet<K>> set, Registry<K> registry) {
		return addField(new DataSetField<>(key, get, set, registry));
	}
	
	public <K> B tagField(CharSequence key, Function<T, Tag<K>> get, BiConsumer<T, Tag<K>> set) {
		return addField(new TagField<>(key, get, set));
	}
	
	public B floatListField(CharSequence key, ToBooleanFunction<T> has, Function<T, FloatList> getCollection) {
		return addField(new FloatListField<>(key, has, getCollection));
	}
	
	public B byteArrayField(CharSequence key, Function<T, byte[]> get, BiConsumer<T, byte[]> set) {
		return addField(new ByteArrayField<>(key, get, set));
	}
	
	public B stringListField(CharSequence key, ToBooleanFunction<T> has, Function<T, List<String>> getCollection) {
		return addField(new StringListField<>(key, has, getCollection));
	}
	
	public B booleanListField(CharSequence key, ToBooleanFunction<T> has, Function<T, BooleanList> getCollection) {
		return addField(new BooleanListField<>(key, has, getCollection));
	}
	
	public B intListField(CharSequence key, ToBooleanFunction<T> has, Function<T, IntList> getCollection) {
		return addField(new IntListField<>(key, has, getCollection));
	}
	
	public B vector3f(CharSequence key, Function<T, Vector3f> get, BiConsumer<T, Vector3f> set) {
		return addField(new Vector3fField<>(key, get, set));
	}
	
	public B vector3d(CharSequence key, Function<T, Vector3d> get, BiConsumer<T, Vector3d> set) {
		return addField(new Vector3dField<>(key, get, set));
	}
	
	public B quaternionf(CharSequence key, Function<T, Quaternionf> get, BiConsumer<T, Quaternionf> set) {
		return addField(new QuaternionfField<>(key, get, set));
	}
	
	public B beginComponent(CharSequence key) {
		// TODO begin
		return getThis();
	}
	
	public B endComponent() {
		// TODO end
		return getThis();
	}
	
	protected abstract B getThis();

}
