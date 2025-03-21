package de.atlascore.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.component.BlockDataComponent;
import de.atlasmc.inventory.component.ComponentType;

public class CoreBlockDataComponent extends CoreAbstractBlockDataComponent implements BlockDataComponent {

	public CoreBlockDataComponent(NamespacedKey key) {
		super(key);
	}

	@Override
	public CoreBlockDataComponent clone() {
		return (CoreBlockDataComponent) super.clone();
	}
	
	@Override
	public ComponentType getType() {
		return ComponentType.BLOCK_STATE;
	}
	
	@Override
	public boolean isServerOnly() {
		return false;
	}

}
