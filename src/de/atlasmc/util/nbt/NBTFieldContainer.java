package de.atlasmc.util.nbt;

import java.util.HashMap;

import de.atlasmc.util.Validate;

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
		Validate.notNull(key, "Key can not be null!");
		Validate.notNull(field, "Field can not be null!");
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
		Validate.notNull(key, "Key can not be null!");
		Validate.notNull(container, "Container can not be null!");
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
