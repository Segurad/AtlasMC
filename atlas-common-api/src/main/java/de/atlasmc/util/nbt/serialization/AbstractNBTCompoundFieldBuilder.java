package de.atlasmc.util.nbt.serialization;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.ObjDoubleConsumer;
import java.util.function.ObjIntConsumer;
import java.util.function.ObjLongConsumer;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

import de.atlasmc.NamespacedKey;
import de.atlasmc.NamespacedKey.Namespaced;
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
import de.atlasmc.util.nbt.serialization.fields.CompoundTypeField;
import de.atlasmc.util.nbt.serialization.fields.DoubleField;
import de.atlasmc.util.nbt.serialization.fields.FloatField;
import de.atlasmc.util.nbt.serialization.fields.IntField;
import de.atlasmc.util.nbt.serialization.fields.LongField;
import de.atlasmc.util.nbt.serialization.fields.MapNamespaced2Int;
import de.atlasmc.util.nbt.serialization.fields.MapNamespacedType;
import de.atlasmc.util.nbt.serialization.fields.NBTField;
import de.atlasmc.util.nbt.serialization.fields.NamespacedKeyField;
import de.atlasmc.util.nbt.serialization.fields.ShortField;
import de.atlasmc.util.nbt.serialization.fields.StringField;
import it.unimi.dsi.fastutil.objects.Object2IntMap;

public abstract class AbstractNBTCompoundFieldBuilder<T, B extends AbstractNBTCompoundFieldBuilder<T, B>> {
	
	private static final int TYPE_COUNT = TagType.getValues().size() - 1; // tag end (0) is not used
	
	private List<Map<CharKey, NBTField<T>>> typeFields = new ArrayList<>(TYPE_COUNT);
	
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
	
	public B intTag(CharSequence key, ToIntFunction<T> supplier, ObjIntConsumer<T> consumer) {
		return addField(new IntField<>(key, supplier, consumer));
	}
	
	public B intTag(CharSequence key, ToIntFunction<T> supplier, ObjIntConsumer<T> consumer, int defaultValue) {
		return addField(new IntField<>(key, supplier, consumer, defaultValue));
	}
	
	public B byteTag(CharSequence key, ToByteFunction<T> supplier, ObjByteConsumer<T> consumer) {
		return addField(new ByteField<>(key, supplier, consumer));
	}
	
	public B byteTag(CharSequence key, ToByteFunction<T> supplier, ObjByteConsumer<T> consumer, byte defaultValue) {
		return addField(new ByteField<>(key, supplier, consumer, defaultValue));
	}
	
	public B longTag(CharSequence key, ToLongFunction<T> supplier, ObjLongConsumer<T> consumer) {
		return addField(new LongField<>(key, supplier, consumer));
	}
	
	public B longTag(CharSequence key, ToLongFunction<T> supplier, ObjLongConsumer<T> consumer, long defaultValue) {
		return addField(new LongField<>(key, supplier, consumer, defaultValue));
	}
	
	public B shortTag(CharSequence key, ToShortFunction<T> supplier, ObjShortConsumer<T> consumer) {
		return addField(new ShortField<>(key, supplier, consumer));
	}
	
	public B shortTag(CharSequence key, ToShortFunction<T> supplier, ObjShortConsumer<T> consumer, short defaultValue) {
		return addField(new ShortField<>(key, supplier, consumer, defaultValue));
	}
	
	public B doubleTag(CharSequence key, ToDoubleFunction<T> supplier, ObjDoubleConsumer<T> consumer) {
		return addField(new DoubleField<>(key, supplier, consumer));
	}
	
	public B doubleTag(CharSequence key, ToDoubleFunction<T> supplier, ObjDoubleConsumer<T> consumer, double defaultValue) {
		return addField(new DoubleField<>(key, supplier, consumer, defaultValue));
	}
	
	public B floatTag(CharSequence key, ToFloatFunction<T> supplier, ObjFloatConsumer<T> consumer) {
		return addField(new FloatField<>(key, supplier, consumer));
	}
	
	public B floatTag(CharSequence key, ToFloatFunction<T> supplier, ObjFloatConsumer<T> consumer, float defaultValue) {
		return addField(new FloatField<>(key, supplier, consumer, defaultValue));
	}
	
	public B boolTag(CharSequence key, ToBooleanFunction<T> supplier, ObjBooleanConsumer<T> consumer) {
		return addField(new BooleanField<>(key, supplier, consumer));
	}
	
	public B boolTag(CharSequence key, ToBooleanFunction<T> supplier, ObjBooleanConsumer<T> consumer, boolean defaultValue) {
		return addField(new BooleanField<>(key, supplier, consumer, defaultValue));
	}
	
	public B stringTag(CharSequence key, Function<T, String> supplier, BiConsumer<T, String> consumer) {
		return addField(new StringField<>(key, supplier, consumer));
	}
	
	public B namespacedKeyTag(CharSequence key, Function<T, NamespacedKey> supplier, BiConsumer<T, NamespacedKey> consumer) {
		return addField(new NamespacedKeyField<>(key, supplier, consumer));
	}
	
	public <K extends NBTSerializable> B compoundTypeTag(CharSequence key, Function<T, K> supplier, BiConsumer<T, K> consumer, NBTSerializationHandler<K> handler) {
		return addField(new CompoundTypeField<>(key, supplier, consumer, handler));
	}
	
	public <K extends Namespaced> B compoundMapNamespaced2Int(CharSequence key, Function<T, Object2IntMap<K>> mapSupplier, Function<String, K> keySupplier) {
		return addField(new MapNamespaced2Int<>(key, mapSupplier, keySupplier));
	}
	
	public <K extends Namespaced & NBTSerializable> B compoundMapNamespacedType(CharSequence key, Function<T, Map<NamespacedKey, K>> mapSupplier, NBTSerializationHandler<K> handler) {
		return addField(new MapNamespacedType<>(key, mapSupplier, handler));
	}
	
	public B beginComponent(CharSequence key) {
		
		return getThis();
	}
	
	public B endComponent() {
		return getThis();
	}
	
	protected abstract B getThis();

}
