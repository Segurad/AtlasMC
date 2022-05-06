package de.atlasmc.util.nbt;

import java.util.HashMap;

import de.atlasmc.util.map.key.CharKey;

/**
 * Stores {@link NBTField} and {@link NBTFieldContainer}
 */
public class NBTFieldContainer {
	
	private HashMap<CharSequence, NBTField> fields;
	private HashMap<CharSequence, NBTFieldContainer> container;
	private NBTField defaultFieldHandler;
	
	public NBTField getField(CharSequence key) {
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
	public NBTFieldContainer setField(CharKey key, NBTField field) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		if (field == null) 
			throw new IllegalArgumentException("Field can not be null!");
		if (this.fields == null) this.fields= new HashMap<CharSequence, NBTField>();
		this.fields.put(key, field);
		return this;
	}
	
	public NBTFieldContainer getContainer(CharSequence key) {
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
	public NBTFieldContainer setContainer(CharKey key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		return setContainer(key, new NBTFieldContainer());
	}
	
	/**
	 * Sets a {@link NBTFieldContainer} for the Key and returns it
	 * @param key name key for this container
	 * @param container the container that should be set
	 * @return the set container
	 */
	public NBTFieldContainer setContainer(CharKey key, NBTFieldContainer container) {
		if (key == null) 
			throw new IllegalArgumentException("Key can not be null!");
		if (container == null) 
			throw new IllegalArgumentException("Container can not be null!");
		if (this.container == null) this.container = new HashMap<CharSequence, NBTFieldContainer>();
		this.container.put(key, container);
		return container;
	}

	public boolean hasUnknownFieldHandler() {
		return defaultFieldHandler != null;
	}
	
	public NBTField getUnknownFieldHandler() {
		return defaultFieldHandler;
	}
	
	public void setUnknownFieldHandler(NBTField field) {
		this.defaultFieldHandler = field;
	}
	

}
