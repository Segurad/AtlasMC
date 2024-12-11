package de.atlasmc.util.nbt;

import de.atlasmc.util.map.key.CharKey;

/**
 * Stores {@link NBTField} and {@link NBTFieldContainer}
 * Overrides fields with the same key of the parent container
 */
class ChildNBTFieldContainer<H> extends NBTFieldContainer<H> {
	
	private final NBTFieldContainer<?> parent;
	
	ChildNBTFieldContainer(NBTFieldContainer<?> parent) {
		if (parent == null) 
			throw new IllegalArgumentException("Parent NBTFieldContainer can not be null!");
		this.parent = parent;
	}
	
	public NBTFieldContainer<?> getParent() {
		return parent;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public NBTField<H> getField(CharSequence key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		NBTField<H> field = super.getField(key);
		if (field != null) 
			return field;
		return (NBTField<H>) parent.getField(key);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public NBTFieldContainer<H> getContainer(CharSequence key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		NBTFieldContainer<H> container = super.getContainer(key);
		if (container != null) 
			return container;
		return (NBTFieldContainer<H>) parent.getContainer(key);
	}
	
	/**
	 * Sets a {@link ChildNBTFieldContainer} if a container with this key is present in the parent container
	 * otherwise it will set a {@link NBTFieldContainer} if the key is not present
	 * @param key of the container
	 * @return the new set container
	 */
	@SuppressWarnings("unchecked")
	public NBTFieldContainer<H> setContainer(CharKey key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		NBTFieldContainer<H> container = super.getContainer(key);
		if (container != null)
			return container;
		container = (NBTFieldContainer<H>) parent.getContainer(key);
		if (container != null) {
			container = super.setContainer(key, container.fork());
		} else {
			container = super.setContainer(key);
		}
		return container;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public NBTField<H> getUnknownFieldHandler() {
		NBTField<H> field = super.getUnknownFieldHandler();
		if (field != null) 
			return field;
		return (NBTField<H>) parent.getUnknownFieldHandler();
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
