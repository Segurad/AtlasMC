package de.atlasmc.util.nbt;

import java.util.HashMap;
import java.util.Map;

import de.atlasmc.util.map.key.CharKey;

/**
 * Stores {@link NBTField} and {@link NBTFieldContainer}
 * @param <H> the type of the holder
 */
public class NBTFieldContainer<H extends NBTHolder> {
	
	private Map<CharSequence, NBTField<H>> fields;
	private Map<CharSequence, NBTFieldContainer<H>> container;
	private NBTField<H> defaultFieldHandler;
	
	public NBTField<H> getField(CharSequence key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (!hasFields()) 
			return null;
		return fields.get(key);
	}
	
	public boolean hasFields() {
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
		if (this.fields == null) 
			this.fields= new HashMap<>();
		this.fields.put(key, field);
		return this;
	}
	
	public NBTFieldContainer<H> getContainer(CharSequence key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (!hasContainer()) 
			return null;
		return container.get(key);
	}
	
	public boolean hasContainer() {
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
		if (this.container == null) 
			this.container = new HashMap<>();
		this.container.put(key, container);
		return container;
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
	

}
