package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;

public abstract class AbstractItemComponent implements ItemComponent {

	protected final NamespacedKey key;
	
	public AbstractItemComponent(NamespacedKey key) {
		if (key == null)
			throw new IllegalArgumentException("Key can not be null!");
		this.key = key;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}

	@Override
	public NamespacedKey getNamespacedKey() {
		return key;
	}
	
	public AbstractItemComponent clone() {
		try {
			return (AbstractItemComponent) super.clone();
		} catch (CloneNotSupportedException e) {}
		return null;
	}

}
