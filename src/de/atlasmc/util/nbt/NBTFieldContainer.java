package de.atlasmc.util.nbt;

import java.util.HashMap;

/**
 * Stores {@link NBTField} and {@link NBTFieldContainer}
 */
public class NBTFieldContainer {
	
	private HashMap<String, NBTField> fields;
	private HashMap<String, NBTFieldContainer> container;
	private NBTField defaultFieldHandler;
	
	public NBTField getField(String key) {
		if (!hasFields()) return null;
		return fields.get(key);
	}
	
	public boolean hasFields() {
		return fields != null && !fields.isEmpty();
	}
	
	public void setField(String key, NBTField field) {
		if (key == null) throw new IllegalArgumentException("Key can not be null!");
		if (field == null) throw new IllegalArgumentException("Field can not be null!");
		if (this.fields == null) this.fields= new HashMap<String, NBTField>();
		this.fields.put(key, field);
	}
	
	public NBTFieldContainer getContainer(String key) {
		if (!hasContainer()) return null;
		return container.get(key);
	}
	
	public boolean hasContainer() {
		return container != null && !container.isEmpty();
	}
	
	public void setContainer(String key, NBTFieldContainer container) {
		if (key == null) throw new IllegalArgumentException("Key can not be null!");
		if (container == null) throw new IllegalArgumentException("Container can not be null!");
		if (this.container == null) this.container = new HashMap<String, NBTFieldContainer>();
		this.container.put(key, container);
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
