package de.atlasmc.util.nbt;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.util.map.key.CharKey;

/**
 * Stores {@link NBTField} and {@link NBTFieldContainer}
 * @param <H> the type of the holder
 */
public class NBTFieldContainer<H> {
	
	private Map<CharSequence, NBTField<H>> fields;
	private Map<CharSequence, NBTFieldContainer<H>> container;
	private NBTField<H> defaultFieldHandler;
	
	protected NBTFieldContainer() {
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
	 * @return this container
	 */
	public NBTFieldContainer<H> setField(CharKey key, NBTField<H> field) {
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
	
	public NBTFieldContainer<H> getContainer(CharSequence key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		Map<CharSequence, NBTFieldContainer<H>> container = this.container;
		if (container == null || container.isEmpty()) 
			return null;
		return container.get(key);
	}
	
	public boolean hasContainer() {
		Map<CharSequence, NBTFieldContainer<H>> container = this.container;
		return container != null && !container.isEmpty();
	}
	
	/**
	 * Creates and sets a new {@link NBTFieldContainer} for the Key and returns it
	 * @param key name key for this container
	 * @return the created container
	 */
	public NBTFieldContainer<H> setContainer(CharKey key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		return setContainer(key, new NBTFieldContainer<>());
	}
	
	/**
	 * Sets a {@link NBTFieldContainer} for the Key and returns it
	 * @param key name key for this container
	 * @param container the container that should be set
	 * @return the set container
	 */
	public NBTFieldContainer<H> setContainer(CharKey key, NBTFieldContainer<H> container) {
		if (key == null) 
			throw new IllegalArgumentException("Key can not be null!");
		if (container == null) 
			throw new IllegalArgumentException("Container can not be null!");
		Map<CharSequence, NBTFieldContainer<H>> containers = this.container;
		if (containers == null) {
			containers = initContainer();
		}
		containers.put(key, container);
		return container;
	}
	
	private synchronized Map<CharSequence, NBTFieldContainer<H>> initContainer() {
		Map<CharSequence, NBTFieldContainer<H>> fields = this.container;
		if (fields == null) {
			fields = this.container = new ConcurrentHashMap<>();
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
	 * Creates a new {@link NBTFieldContainer} containing that as access to all fields of this container.
	 * @param <T>
	 * @return child container
	 */
	public <T extends H> NBTFieldContainer<T> fork() {
		return new ChildNBTFieldContainer<>(this);
	}
	
	public static <H> NBTFieldContainer<H> newContainer() {
		return new NBTFieldContainer<>();
	}

}
