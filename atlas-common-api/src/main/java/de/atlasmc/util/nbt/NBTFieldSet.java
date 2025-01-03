package de.atlasmc.util.nbt;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.util.map.key.CharKey;

/**
 * Stores {@link NBTField} and {@link NBTFieldSet}
 * @param <H> the type of the holder
 */
public class NBTFieldSet<H> {
	
	private Map<CharSequence, NBTField<H>> fields;
	private Map<CharSequence, NBTFieldSet<H>> sets;
	private NBTField<H> defaultFieldHandler;
	
	protected NBTFieldSet() {
		// use create function
	}
	
	public NBTField<H> getField(CharSequence key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		Map<CharSequence, NBTField<H>> fields = this.fields;
		if (fields == null || fields.isEmpty()) 
			return null;
		return fields.get(key);
	}
	
	public boolean hasFields() {
		Map<CharSequence, NBTField<H>> fields = this.fields;
		return fields != null && !fields.isEmpty();
	}
	
	/**
	 * Sets a Field for the Key
	 * @param key
	 * @param field
	 * @return this set
	 */
	public NBTFieldSet<H> setField(CharKey key, NBTField<H> field) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (field == null) 
			throw new IllegalArgumentException("Field can not be null!");
		Map<CharSequence, NBTField<H>> fields = this.fields;
		if (fields == null) {
			fields = initFields();
		}
		fields.put(key, field);
		return this;
	}
	
	private synchronized Map<CharSequence, NBTField<H>> initFields() {
		Map<CharSequence, NBTField<H>> fields = this.fields;
		if (fields == null) {
			fields = this.fields = new ConcurrentHashMap<>();
		}
		return fields;
	}
	
	public NBTFieldSet<H> getSet(CharSequence key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		Map<CharSequence, NBTFieldSet<H>> container = this.sets;
		if (container == null || container.isEmpty()) 
			return null;
		return container.get(key);
	}
	
	public boolean hasSets() {
		Map<CharSequence, NBTFieldSet<H>> container = this.sets;
		return container != null && !container.isEmpty();
	}
	
	/**
	 * Creates and sets a new {@link NBTFieldSet} for the Key and returns it
	 * @param key name key for this set
	 * @return the created set
	 */
	public NBTFieldSet<H> setSet(CharKey key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		return setSet(key, new NBTFieldSet<>());
	}
	
	/**
	 * Sets a {@link NBTFieldSet} for the Key and returns it
	 * @param key name key for this set
	 * @param set the set that should be set
	 * @return the set set
	 */
	public NBTFieldSet<H> setSet(CharKey key, NBTFieldSet<H> set) {
		if (key == null) 
			throw new IllegalArgumentException("Key can not be null!");
		if (set == null) 
			throw new IllegalArgumentException("Set can not be null!");
		Map<CharSequence, NBTFieldSet<H>> sets = this.sets;
		if (sets == null) {
			sets = initSets();
		}
		sets.put(key, set);
		return set;
	}
	
	private synchronized Map<CharSequence, NBTFieldSet<H>> initSets() {
		Map<CharSequence, NBTFieldSet<H>> fields = this.sets;
		if (fields == null) {
			fields = this.sets = new ConcurrentHashMap<>();
		}
		return fields;
	}

	public boolean hasUnknownFieldHandler() {
		return defaultFieldHandler != null;
	}
	
	public NBTField<H> getUnknownFieldHandler() {
		return defaultFieldHandler;
	}
	
	public void setUnknownFieldHandler(NBTField<H> field) {
		this.defaultFieldHandler = field;
	}
	
	/**
	 * Creates a new {@link NBTFieldSet} set that as access to all fields of this set.
	 * @param <T>
	 * @return child set
	 */
	public <T extends H> NBTFieldSet<T> fork() {
		return new ChildNBTFieldSet<>(this);
	}
	
	public static <H> NBTFieldSet<H> newSet() {
		return new NBTFieldSet<>();
	}

}
