package de.atlasmc.util.nbt;

import de.atlasmc.util.map.key.CharKey;

/**
 * Stores {@link NBTField} and {@link NBTFieldContainer}
 * Overrides fields with the same key of the parent container
 */
public class ChildNBTFieldContainer extends NBTFieldContainer {
	
	private final NBTFieldContainer parent;
	
	public ChildNBTFieldContainer(NBTFieldContainer parent) {
		if (parent == null) 
			throw new IllegalArgumentException("Parent NBTFieldContainer can not be null!");
		this.parent = parent;
	}
	
	public NBTFieldContainer getParent() {
		return parent;
	}
	
	@Override
	public NBTField getField(CharSequence key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		NBTField field = super.getField(key);
		if (field != null) return field;
		return parent.getField(key);
	}
	
	@Override
	public NBTFieldContainer getContainer(CharSequence key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		NBTFieldContainer container = super.getContainer(key);
		if (container != null) return container;
		return parent.getContainer(key);
	}
	
	/**
	 * Sets a {@link ChildNBTFieldContainer} if a container with this key is present in the parent container
	 * otherwise it will set a {@link NBTFieldContainer} if the key is not present
	 * @param key of the container
	 * @return the new set container
	 */
	public NBTFieldContainer setChildContainer(CharKey key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		NBTFieldContainer container = getContainer(key);
		if (container == null) 
			return super.setContainer(key);
		return super.setContainer(key, new ChildNBTFieldContainer(container));
	}
	
	@Override
	public NBTField getUnknownFieldHandler() {
		NBTField field = super.getUnknownFieldHandler();
		if (field != null) return field;
		return parent.getUnknownFieldHandler();
	}
	
	@Override
	public boolean hasContainer() {
		return super.hasContainer() || parent.hasContainer();
	}
	
	@Override
	public boolean hasFields() {
		return super.hasFields() || parent.hasFields();
	}
	
	@Override
	public boolean hasUnknownFieldHandler() {
		return super.hasUnknownFieldHandler() || parent.hasUnknownFieldHandler();
	}

}
