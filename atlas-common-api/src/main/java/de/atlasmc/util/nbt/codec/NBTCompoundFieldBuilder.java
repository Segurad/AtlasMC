package de.atlasmc.util.nbt.codec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.atlasmc.util.Builder;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.map.IdentityStrategy;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.codec.field.NBTField;
import de.atlasmc.util.nbt.codec.type.NBTCompoundField;
import de.atlasmc.util.nbt.io.NBTReader;
import de.atlasmc.util.nbt.io.NBTWriter;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import it.unimi.dsi.fastutil.objects.ObjectIterator;

public class NBTCompoundFieldBuilder<T> extends NBTField<T> implements Builder<NBTField<T>> {

	private final List<NBTField<T>> fieldOrder = new ArrayList<>();
	private final List<Map<CharKey, NBTField<T>>> typeFields = new ArrayList<>(AbstractNBTCompoundFieldBuilder.TYPE_COUNT);
	private final Object2IntMap<NBTField<T>> refCounts = new Object2IntLinkedOpenCustomHashMap<>(IdentityStrategy.getInstance());
	public ToBooleanFunction<T> has;
	public NBTCompoundFieldBuilder<T> parent;
	private boolean buildPrepared = true;
	
	public NBTCompoundFieldBuilder() {
		this("root", null, null);
	}
	
	public NBTCompoundFieldBuilder(CharSequence key, ToBooleanFunction<T> has, NBTCompoundFieldBuilder<T> parent) {
		super(key, COMPOUND, true);
		this.has = has;
		this.parent = parent;
		for (int i = 0; i < AbstractNBTCompoundFieldBuilder.TYPE_COUNT; i++) {
			typeFields.add(i, null);
		}
	}
	
	public ToBooleanFunction<T> getHas() {
		return has;
	}
	
	public void setHas(ToBooleanFunction<T> has) {
		this.has = has;
	}

	private void prepareBuild() {
		if (buildPrepared)
			return;
		final ObjectIterator<Entry<NBTField<T>>> iter = refCounts.object2IntEntrySet().iterator();
		Map<NBTField<T>, NBTField<T>> remap = null;
		while (iter.hasNext()) {
			Entry<NBTField<T>> entry = iter.next();
			if (entry.getIntValue() > 0) {
				NBTField<T> field = entry.getKey();
				if (!(field instanceof Builder rawBuilder))
					continue;
				@SuppressWarnings("unchecked")
				Builder<NBTField<T>> builder = rawBuilder;
				if (remap == null)
					remap = new HashMap<>();
				remap.put(field, builder.build());
			}
			final NBTField<T> refField = entry.getKey();
			final int size = fieldOrder.size();
			for (int i = 0; i < size; i++) {
				NBTField<T> field = fieldOrder.get(i);
				if (refField == field) {
					fieldOrder.remove(i);
					break;
				}
			}
			iter.remove();
		}
		remapBuildCompoundFields(remap);
	}
	
	private void remapBuildCompoundFields(Map<NBTField<T>, NBTField<T>> remap) {
		if (remap == null || remap.isEmpty())
			return;
		final Map<CharKey, NBTField<T>> compoundMap = typeFields.get(TagType.COMPOUND.getID() - 1);
		for (Map.Entry<NBTField<T>, NBTField<T>> entry : remap.entrySet()) {
			NBTField<T> oldKey = entry.getKey();
			NBTField<T> key = entry.getValue();
			int count = refCounts.removeInt(oldKey);
			refCounts.put(key, count);
			final int size = fieldOrder.size();
			for (int i = 0; i < size; i++) {
				NBTField<T> field = fieldOrder.get(i);
				if (oldKey == field) {
					fieldOrder.set(i, key);
					break;
				}
			}
			compoundMap.remove(oldKey.key, oldKey);
			addField(key);
		}
	}
	
	public NBTField<T>[] buildFieldsArray() {
		prepareBuild();
		@SuppressWarnings("unchecked")
		NBTField<T>[] array = new NBTField[fieldOrder.size()];
		fieldOrder.toArray(array);
		return array;
	}
	
	public byte[] buildSkipArray() {
		prepareBuild();
		final int size = fieldOrder.size();
		if (size == 0)
			return ByteArrays.EMPTY_ARRAY;
		byte[] array = new byte[size];
		if (size == 1)
			return array;
		byte skips = 0;
		NBTField<T> previous = fieldOrder.get(0);
		for (int i = 1; i < size; i++) {
			NBTField<T> next = fieldOrder.get(i);
			if (next.key.equals(previous.key)) {
				skips++;
			} else if (skips > 0) {
				for (int j = i - skips - 1; j < i - 1; j++) {
					array[j] = skips--;
				}
			}
			previous = next;
		}
		if (skips > 0) {
			for (int j = size - skips; j < size; j++) {
				array[j] = skips--;
			}
		}
		return array;
	}
	
	public Map<CharKey, NBTField<T>>[] buildTypeFieldsArray() {
		prepareBuild();
		@SuppressWarnings("unchecked")
		Map<CharKey, NBTField<T>>[] array = new Map[typeFields.size()];
		for (int i = 0; i < array.length; i++) {
			Map<CharKey, NBTField<T>> map = typeFields.get(i);
			if (map == null)
				continue;
			array[i] = Map.copyOf(map);
		}
		return array;
	}
	
	public NBTCompoundFieldBuilder<T> addField(NBTField<T> field) {
		if (field == null)
			throw new IllegalArgumentException("Field can not be null!");
		if (field.key == null)
			throw new IllegalArgumentException("Key of field can not be null!");
		if (field.types.isEmpty())
			throw new IllegalArgumentException("Types can not be empty!");
		buildPrepared = false;
		int refCount = refCounts.getOrDefault(field, -1);
		if (refCount == -1)
			fieldOrder.add(field);
		for (TagType type : field.types) {
			if (type == TagType.TAG_END)
				throw new IllegalArgumentException("TAG_END is not supported!");
			final int id = type.getID() - 1; // tag end (0) is not used 
			Map<CharKey, NBTField<T>> fields = typeFields.get(id);
			if (fields == null)
				typeFields.set(id, fields = new HashMap<>());
			NBTField<T> old = fields.put(field.key, field);
			refCount++;
			if (old != null) {
				if (old == field) {
					refCount--;
				} else {
					int oldRefCount = refCounts.getInt(old);
					oldRefCount--;
					refCounts.put(old, oldRefCount);
				}
			}
		}
		refCounts.put(field, refCount);
		return this;
	}
	
	@Override
	public boolean serialize(T value, NBTWriter writer, CodecContext context) throws IOException {
		throw new UnsupportedOperationException("Builder");
	}

	@Override
	public void deserialize(T value, NBTReader reader, CodecContext context) throws IOException {
		throw new UnsupportedOperationException("Builder");
	}

	@Override
	public NBTCompoundField<T> build() {
		return new NBTCompoundField<>(this);
	}

	@Override
	public void clear() {
		fieldOrder.clear();
		typeFields.clear();
		refCounts.clear();
		buildPrepared = true;
	}

}
