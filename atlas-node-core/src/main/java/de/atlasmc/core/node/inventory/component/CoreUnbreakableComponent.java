package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.UnbreakableComponent;

public class CoreUnbreakableComponent extends AbstractItemComponent implements UnbreakableComponent {

	public CoreUnbreakableComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreUnbreakableComponent clone() {
		return this;
	}

}
