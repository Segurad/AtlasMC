package de.atlasmc.nbt.codec.field;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.atlasmc.nbt.TagType;
import de.atlasmc.nbt.codec.AbstractNBTCompoundFieldBuilder;
import de.atlasmc.nbt.codec.CodecTags;
import de.atlasmc.util.Builder;
import de.atlasmc.util.function.ToBooleanFunction;
import de.atlasmc.util.map.IdentityStrategy;
import de.atlasmc.util.map.key.CharKey;
import it.unimi.dsi.fastutil.bytes.ByteArrays;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;

public class NBTCompoundFieldBuilder<T> extends NBTFieldBuilder<T, NBTCompoundFieldBuilder<T>> implements AbstractNBTCompoundFieldBuilder<T, NBTCompoundFieldBuilder<T>> {

	private final List<NBTField<T>> fieldOrder = new ArrayList<>();
	private final List<Map<CharKey, NBTField<T>>> typeFields = new ArrayList<>(AbstractNBTCompoundFieldBuilder.TYPE_COUNT);
	private final Object2IntMap<NBTField<T>> refCounts = new Object2IntLinkedOpenCustomHashMap<>(IdentityStrategy.getInstance());
	public ToBooleanFunction<T> has;
	public final NBTCompoundFieldBuilder<T> parent;
	private boolean buildPrepared = true;
	
	public NBTCompoundFieldBuilder() {
		this(null, (CharSequence) null);
	}
	
	private NBTCompoundFieldBuilder(NBTCompoundFieldBuilder<T> parent, CharSequence key) {
		setKey(key);
		this.parent = parent;
		for (int i = 0; i < AbstractNBTCompoundFieldBuilder.TYPE_COUNT; i++) {
			typeFields.add(i, null);
		}
	}
	
	private NBTCompoundFieldBuilder(NBTCompoundFieldBuilder<T> parent, CharSequence key, ToBooleanFunction<T> has, boolean serverOnly) {
		this(parent, key);
		this.has = has;
		setServerOnly(serverOnly);
	}
	
	private NBTCompoundFieldBuilder(NBTCompoundFieldBuilder<T> parent, NBTCompoundField<T> field) {
		this(parent, field.key);
		field.builder(this);
	}
	
	private NBTField<T> getField(CharSequence key, TagType type) {
		int id = type.getID() - 1;
		var map = typeFields.get(id);
		if (map == null)
			return null;
		return map.get(CharKey.of(key));
	}
	
	private void replaceField(NBTField<T> field, NBTField<T> newField) {
		buildPrepared = false;
		int refCount = refCounts.getOrDefault(newField, -1);
		if (refCount == -1) {
			if (field == null) {
				fieldOrder.add(newField);
			} else {
				final int size = fieldOrder.size();
				for (int i = 0; i < size; i++) {
					if (fieldOrder.get(i) == field) {
						fieldOrder.set(i, newField);
					}
				}
			}
		}
		refCount = 0;
		for (TagType type : newField.types) {
			if (type == TagType.TAG_END)
				throw new IllegalArgumentException("TAG_END is not supported!");
			final int id = type.getID() - 1; // tag end (0) is not used 
			Map<CharKey, NBTField<T>> fields = typeFields.get(id);
			if (fields == null)
				typeFields.set(id, fields = new HashMap<>());
			NBTField<T> old = fields.put(newField.key, newField);
			refCount++;
			if (old != null) {
				if (old == newField) {
					refCount--;
				} else {
					int oldRefCount = refCounts.getInt(old);
					oldRefCount--;
					refCounts.put(old, oldRefCount);
				}
			}
		}
		refCounts.put(field, refCount);
	}
	
	@Override
	public List<TagType> getTypes() {
		return CodecTags.COMPOUND;
	}
	
	public ToBooleanFunction<T> getHasData() {
		return has;
	}
	
	public NBTCompoundFieldBuilder<T> setHasData(ToBooleanFunction<T> hasData) {
		this.has = hasData;
		return this;
	}

	private void prepareBuild() {
		if (buildPrepared)
			return;
		final var iter = refCounts.object2IntEntrySet().iterator();
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
			} else {
				var refField = entry.getKey();
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
		replaceField(null, field);
		return this;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public NBTCompoundFieldBuilder<T> beginComponent(CharSequence key, ToBooleanFunction<T> has, boolean serverOnly) {
		var field = getField(key, TagType.COMPOUND);
		if (field instanceof BuilderField rawBuilder) {
			@SuppressWarnings("rawtypes")
			Builder builder = rawBuilder.getBuilder();
			if (builder instanceof NBTCompoundFieldBuilder compBuilder) {
				return compBuilder;
			}
		} else if (field instanceof NBTCompoundField comp) {
			var builder = new NBTCompoundFieldBuilder<>(this, comp);
			replaceField(field, new BuilderField<>(key, CodecTags.COMPOUND, serverOnly, builder));
			return builder;
		}
		var builder = new NBTCompoundFieldBuilder<>(this, key, has, serverOnly);
		if (field != null) {
			replaceField(field, new BuilderField<>(key, CodecTags.COMPOUND, serverOnly, builder));
		} else {
			addField(new BuilderField<>(key, CodecTags.COMPOUND, serverOnly, builder));
		}
		return builder;
	}
	
	@Override
	public NBTCompoundFieldBuilder<T> endComponent() {
		if (parent == null)
			throw new IllegalStateException("No component to end!");
		return parent;
	}
	
	public boolean hasParent() {
		return parent != null;
	}

	@Override
	public NBTCompoundField<T> build() {
		return new NBTCompoundField<>(this);
	}
	
	@Override
	public NBTCompoundFieldBuilder<T> getThis() {
		return this;
	}

}
