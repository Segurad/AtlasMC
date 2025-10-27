package de.atlasmc.node.inventory.component;

import de.atlasmc.util.CloneException;

public abstract class AbstractItemComponent implements ItemComponent {

	protected final ComponentType type;
	
	public AbstractItemComponent(ComponentType type) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		this.type = type;
	}
	
	@Override
	public ComponentType getType() {
		return type;
	}
	
	public AbstractItemComponent clone() {
		try {
			return (AbstractItemComponent) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new CloneException(e);
		}
	}

}
