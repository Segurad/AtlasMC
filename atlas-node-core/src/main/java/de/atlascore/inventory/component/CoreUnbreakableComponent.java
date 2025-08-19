package de.atlascore.inventory.component;

import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.UnbreakableComponent;

public class CoreUnbreakableComponent extends AbstractItemComponent implements UnbreakableComponent {

	public CoreUnbreakableComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreUnbreakableComponent clone() {
		return this;
	}

}
