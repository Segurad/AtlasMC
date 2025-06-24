package de.atlasmc.util.nbt.serialization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import de.atlasmc.Color;
import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatColor;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.registry.Registry;
import de.atlasmc.util.EnumName;
import de.atlasmc.util.function.ObjBooleanConsumer;
import de.atlasmc.util.function.ObjByteConsumer;
import de.atlasmc.util.function.ObjFloatConsumer;
import de.atlasmc.util.function.ObjShortConsumer;
import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.function.ToByteFunction;
import de.atlasmc.util.function.ToFloatFunction;
import de.atlasmc.util.function.ToShortFunction;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTException;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.serialization.fields.BooleanField;
import de.atlasmc.util.nbt.serialization.fields.ByteField;
import de.atlasmc.util.nbt.serialization.fields.ChatColorColorField;
import de.atlasmc.util.nbt.serialization.fields.CompoundTypeField;
import de.atlasmc.util.nbt.serialization.fields.DoubleField;
import de.atlasmc.util.nbt.serialization.fields.FloatField;
import de.atlasmc.util.nbt.serialization.fields.IntColorField;
import de.atlasmc.util.nbt.serialization.fields.IntField;
import de.atlasmc.util.nbt.serialization.fields.ListColorField;
import de.atlasmc.util.nbt.serialization.fields.LongField;
import de.atlasmc.util.nbt.serialization.fields.MapNamespaced2Int;
import de.atlasmc.util.nbt.serialization.fields.MapNamespacedType;
import de.atlasmc.util.nbt.serialization.fields.NBTField;
import de.atlasmc.util.nbt.serialization.fields.NamedEnumField;
import de.atlasmc.util.nbt.serialization.fields.NamespacedKeyField;
import de.atlasmc.util.nbt.serialization.fields.RawField;
import de.atlasmc.util.nbt.serialization.fields.RegistryValueField;
import de.atlasmc.util.nbt.serialization.fields.ShortField;
import de.atlasmc.util.nbt.serialization.fields.StringField;
import de.atlasmc.util.nbt.serialization.fields.StringToObjectField;
import de.atlasmc.util.nbt.serialization.fields.TypeListField;
import de.atlasmc.util.nbt.serialization.fields.UUIDIntArrayField;
import de.atlasmc.util.nbt.serialization.fields.UUIDStringField;
import de.atlasmc.util.nbt.tag.NBT;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

public abstract class AbstractNBTCompoundFieldBuilder<T, B extends AbstractNBTCompoundFieldBuilder<T, B>> {
	
	private static final int TYPE_COUNT = TagType.getValues().size() - 1; // tag end (0) is not used
	
	private List<Map<CharKey, NBTField<T>>> typeFields = new ArrayList<>(TYPE_COUNT);
	
	private static final Function<Chat, String> chatToString = Chat::toText;
	private static final Function<String, Chat> stringToChat = ChatUtil::toChat;
	
	public B addField(NBTField<T> field) {
		final int id = field.type.getID() - 1; // tag end (0) is not used 
		Map<CharKey, NBTField<T>> fields = typeFields.get(id);
		if (fields == null)
			typeFields.set(id, fields = new HashMap<>());
		if (field.key == null)
			throw new NBTException("Key of field can not be null!");
		fields.put(field.key, field);
		return getThis();
	}
	
	public B intTag(CharSequence key, ToIntFunction<T> get, ObjIntConsumer<T> set) {
		return addField(new IntField<>(key, get, set));
	}
	
	public B intTag(CharSequence key, ToIntFunction<T> get, ObjIntConsumer<T> set, int defaultValue) {
		return addField(new IntField<>(key, get, set, defaultValue));
	}
	
	public B byteTag(CharSequence key, ToByteFunction<T> get, ObjByteConsumer<T> set) {
		return addField(new ByteField<>(key, get, set));
	}
	
	public B byteTag(CharSequence key, ToByteFunction<T> get, ObjByteConsumer<T> set, byte defaultValue) {
		return addField(new ByteField<>(key, get, set, defaultValue));
	}
	
	public B longTag(CharSequence key, ToLongFunction<T> get, ObjLongConsumer<T> set) {
		return addField(new LongField<>(key, get, set));
	}
	
	public B longTag(CharSequence key, ToLongFunction<T> get, ObjLongConsumer<T> set, long defaultValue) {
		return addField(new LongField<>(key, get, set, defaultValue));
	}
	
	public B shortTag(CharSequence key, ToShortFunction<T> get, ObjShortConsumer<T> set) {
		return addField(new ShortField<>(key, get, set));
	}
	
	public B shortTag(CharSequence key, ToShortFunction<T> get, ObjShortConsumer<T> set, short defaultValue) {
		return addField(new ShortField<>(key, get, set, defaultValue));
	}
	
	public B doubleTag(CharSequence key, ToDoubleFunction<T> get, ObjDoubleConsumer<T> set) {
		return addField(new DoubleField<>(key, get, set));
	}
	
	public B doubleTag(CharSequence key, ToDoubleFunction<T> get, ObjDoubleConsumer<T> set, double defaultValue) {
		return addField(new DoubleField<>(key, get, set, defaultValue));
	}
	
	public B floatTag(CharSequence key, ToFloatFunction<T> get, ObjFloatConsumer<T> set) {
		return addField(new FloatField<>(key, get, set));
	}
	
	public B floatTag(CharSequence key, ToFloatFunction<T> get, ObjFloatConsumer<T> set, float defaultValue) {
		return addField(new FloatField<>(key, get, set, defaultValue));
	}
	
	public B boolTag(CharSequence key, ToBooleanFunction<T> get, ObjBooleanConsumer<T> set) {
		return addField(new BooleanField<>(key, get, set));
	}
	
	public B bool(CharSequence key, ToBooleanFunction<T> get, ObjBooleanConsumer<T> set, boolean defaultValue) {
		return addField(new BooleanField<>(key, get, set, defaultValue));
	}
	
	public B string(CharSequence key, Function<T, String> get, BiConsumer<T, String> set) {
		return addField(new StringField<>(key, get, set));
	}
	
	public <K extends Namespaced> B registryValue(CharSequence key, Function<T, K> get, BiConsumer<T, K> set, Registry<K> registry) {
		return addField(new RegistryValueField<>(key, get, set, registry));
	}
	
	public <K extends Namespaced & NBTSerializable> B registryValue(CharSequence key, Function<T, K> get, BiConsumer<T, K> set, Registry<K> registry, NBTSerializationHandler<K> handler) {
		registryValue(key, get, set, registry);
		return compoundType(key, get, set, handler);
	}
	
	public B namespacedKey(CharSequence key, Function<T, NamespacedKey> get, BiConsumer<T, NamespacedKey> set) {
		return addField(new NamespacedKeyField<>(key, get, set));
	}
	
	public <K extends NBTSerializable> B compoundType(CharSequence key, Function<T, ? super K> get, BiConsumer<T, ? super K> set, NBTSerializationHandler<K> handler) {
		return addField(new CompoundTypeField<>(key, get, set, handler));
	}
	
	public <K extends Namespaced> B compoundMapNamespaced2Int(CharSequence key, Function<T, Object2IntMap<K>> mapSupplier, Function<String, K> keySupplier) {
		return addField(new MapNamespaced2Int<>(key, mapSupplier, keySupplier));
	}
	
	public <K extends Namespaced & NBTSerializable> B compoundMapNamespacedType(CharSequence key, Function<T, Map<NamespacedKey, K>> mapSupplier, NBTSerializationHandler<K> handler) {
		return addField(new MapNamespacedType<>(key, mapSupplier, handler));
	}
	
	public B color(CharSequence key, Function<T, Color> get, BiConsumer<T, Color> set) {
		addField(new IntColorField<>(key, get, set));
		return addField(new ListColorField<>(key, get, set));
	}
	
	public B chatColorColor(CharSequence key, Function<T, ChatColor> get, BiConsumer<T, ChatColor> set, Function<T, Color> getColor, BiConsumer<T, Color> setColor) {
		return addField(new ChatColorColorField<>(key, get, set, getColor, setColor));
	}
	
	public <K> B stringToObject(CharSequence key, Function<T, K> get, BiConsumer<T, K> set, Function<String, K> stringToObject, Function<K, String> objectToString) {
		return addField(new StringToObjectField<>(key, get, set, stringToObject, objectToString));
	}
	
	public <K extends Enum<?> & EnumName> B namedEnumField(CharSequence key, Function<T, K> get, BiConsumer<T, K> set, Function<String, K> enumSupplier, K defaultValue) {
		return addField(new NamedEnumField<>(key, get, set, enumSupplier, defaultValue));
	}
	
	public B chat(CharSequence key, Function<T, Chat> get, BiConsumer<T, Chat> set) {
		compoundType(key, get, set, ChatComponent.NBT_HANDLER);			
		return stringToObject(key, get, set, stringToChat, chatToString);
		// list format will be ignored
	}
	
	public B rawField(CharSequence key, TagType type, Function<T, NBT> get, BiConsumer<T, NBT> set, boolean includeKey) {
		return addField(new RawField<>(key, type, get, set, includeKey));
	}
	
	public B uuid(CharSequence key, Function<T, UUID> get, BiConsumer<T, UUID> set) {
		addField(new UUIDIntArrayField<>(key, get, set));
		return addField(new UUIDStringField<>(key, get, set));
		// list of int will be ignored
	}
	

	public <K extends NBTSerializable> B typeList(CharSequence key, Function<T, List<K>> listSupplier, NBTSerializationHandler<K> handler) {
		return typeList(key, listSupplier, handler, true);
	}
	
	public <K extends NBTSerializable> B typeList(CharSequence key, Function<T, List<K>> listSupplier, NBTSerializationHandler<K> handler, boolean optional) {
		return addField(new TypeListField<>(key, listSupplier, handler, optional));
	}
	
	public B beginComponent(CharSequence key) {
		
		return getThis();
	}
	
	public B endComponent() {
		return getThis();
	}
	
	protected abstract B getThis();

}
