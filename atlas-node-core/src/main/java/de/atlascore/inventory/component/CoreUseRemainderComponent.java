package de.atlascore.inventory.component;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.component.AbstractItemComponent;
import de.atlasmc.inventory.component.ComponentType;
import de.atlasmc.inventory.component.UseRemainderComponent;

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
