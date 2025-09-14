package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.inventory.component.BlockDataComponent;
import de.atlasmc.node.inventory.component.ComponentType;

public class CoreBlockDataComponent extends CoreAbstractBlockDataComponent implements BlockDataComponent {

	public CoreBlockDataComponent(ComponentType type) {
		super(type);
	}

	@Override
	public CoreBlockDataComponent clone() {
		return (CoreBlockDataComponent) super.clone();
	}

}
