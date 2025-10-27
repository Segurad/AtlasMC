package de.atlasmc.core.node.inventory.component;

import java.util.ArrayList;
import java.util.List;

import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.BundleContentsComponent;
import de.atlasmc.node.inventory.component.ComponentType;

public class CoreBundleContentsComponent extends AbstractItemComponent implements BundleContentsComponent {

	private static final int DEFAULT_ITEMS_SIZE = 5;
	
	private List<ItemStack> items;
	
	public CoreBundleContentsComponent(ComponentType type) {
		super(type);
	}

	@Override
	public CoreBundleContentsComponent clone() {
		CoreBundleContentsComponent clone = (CoreBundleContentsComponent) super.clone();
		if (clone == null)
			return null;
		return clone;
	}

	@Override
	public List<ItemStack> getItems() {
		if (items == null)
			items = new ArrayList<>(DEFAULT_ITEMS_SIZE);
		return items;
	}

	@Override
	public boolean hasItems() {
		return items != null && !items.isEmpty();
	}

	@Override
	public void addItem(ItemStack item) {
		if (item == null)
			throw new IllegalArgumentException("Item can not be null!");
		getItems().add(item);
	}

	@Override
	public boolean removeItem(ItemStack item) {
		if (items == null)
			return false;
		return items.remove(item);
	}

}
