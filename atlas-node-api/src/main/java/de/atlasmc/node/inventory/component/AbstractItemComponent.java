package de.atlasmc.node.inventory.component;

import java.util.Objects;

import de.atlasmc.util.CloneException;

public abstract class AbstractItemComponent implements ItemComponent {

	protected final ComponentType type;
	
	public AbstractItemComponent(ComponentType type) {
		this.type = Objects.requireNonNull(type, "type");
	}
	
	@Override
	public ComponentType getType() {
		return type;
	}
	
	@Override
	public AbstractItemComponent clone() {
		try {
			return (AbstractItemComponent) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new CloneException(e);
		}
	}

}
