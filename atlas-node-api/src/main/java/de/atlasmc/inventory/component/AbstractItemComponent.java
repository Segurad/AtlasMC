package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.AbstractNBTBase;

public abstract class AbstractItemComponent extends AbstractNBTBase implements ItemComponent {

	protected final NamespacedKey key;
	
	public AbstractItemComponent(NamespacedKey key) {
		this.key = key;
	}
	
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
