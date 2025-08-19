package de.atlascore.inventory.component;

import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.MaxStackSizeComponent;

public class CoreMaxStackSizeComponent extends AbstractItemComponent implements MaxStackSizeComponent {

	private int maxStackSize;
	
	public CoreMaxStackSizeComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreMaxStackSizeComponent clone() {
		return (CoreMaxStackSizeComponent) super.clone();
	}

	@Override
	public int getMaxStackSize() {
		return maxStackSize;
	}

	@Override
	public void setMaxStackSize(int stackSize) {
		this.maxStackSize = stackSize;
	}

}
