package de.atlasmc.util.nbt;

import de.atlasmc.util.map.key.CharKey;

/**
 * Stores {@link NBTField} and {@link NBTFieldSet}
 * Overrides fields with the same key of the parent set
 */
class ChildNBTFieldSet<H> extends NBTFieldSet<H> {
	
	private final NBTFieldSet<?> parent;
	
	ChildNBTFieldSet(NBTFieldSet<?> parent) {
		if (parent == null) 
			throw new IllegalArgumentException("Parent can not be null!");
		this.parent = parent;
	}
	
	public NBTFieldSet<?> getParent() {
		return parent;
	}
	
	@Override
	public NBTField<H> getField(CharSequence key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		NBTField<H> field = super.getField(key);
		if (field != null) 
			return field;
		@SuppressWarnings("unchecked")
		NBTField<H> parentField = (NBTField<H>) parent.getField(key);
		return parentField;
	}
	
	@Override
	public NBTFieldSet<H> getSet(CharSequence key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		NBTFieldSet<H> container = super.getSet(key);
		if (container != null) 
			return container;
		@SuppressWarnings("unchecked")
		NBTFieldSet<H> parentSet = (NBTFieldSet<H>) parent.getSet(key);
		return parentSet;
	}
	
	/**
	 * Sets a {@link ChildNBTFieldSet} if a set with this key is present in the parent set
	 * otherwise it will set a {@link NBTFieldSet} if the key is not present
	 * @param key of the set
	 * @return the new set set
	 */
	public NBTFieldSet<H> setSet(CharKey key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		NBTFieldSet<H> container = super.getSet(key);
		if (container != null)
			return container;
		@SuppressWarnings("unchecked")
		NBTFieldSet<H> parentSet = (NBTFieldSet<H>) parent.getSet(key);
		if (parentSet != null) {
			container = super.setSet(key, parentSet.fork());
		} else {
			container = super.setSet(key);
		}
		return container;
	}
	
	@Override
	public NBTField<H> getUnknownFieldHandler() {
		NBTField<H> field = super.getUnknownFieldHandler();
		if (field != null) 
			return field;
		@SuppressWarnings("unchecked")
		NBTField<H> parentFields = (NBTField<H>) parent.getUnknownFieldHandler();
		return parentFields;
	}
	
	@Override
	public boolean hasSets() {
		return super.hasSets() || parent.hasSets();
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
