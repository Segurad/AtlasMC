package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.UseRemainderComponent;

public class CoreUseRemainderComponent extends AbstractItemComponent implements UseRemainderComponent {

	private ItemStack item;
	
	public CoreUseRemainderComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public CoreUseRemainderComponent clone() {
		CoreUseRemainderComponent clone = (CoreUseRemainderComponent) super.clone();
		if (item == null)
			clone.item = item.clone();
		return clone;
	}

	@Override
	public ItemStack getItem() {
		return item;
	}

	@Override
	public void setItem(ItemStack item) {
		this.item = item;
	}

}
