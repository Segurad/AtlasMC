package de.atlascore.inventory.component;

import de.atlasmc.inventory.component.BlockDataComponent;
import de.atlasmc.inventory.component.ComponentType;

public class CoreBlockDataComponent extends CoreAbstractBlockDataComponent implements BlockDataComponent {

	public CoreBlockDataComponent(ComponentType type) {
		super(type);
	}

	@Override
	public CoreBlockDataComponent clone() {
		return (CoreBlockDataComponent) super.clone();
	}

}
