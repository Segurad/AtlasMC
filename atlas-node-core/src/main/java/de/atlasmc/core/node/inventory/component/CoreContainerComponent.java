package de.atlasmc.core.node.inventory.component;

import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.ItemType;
import de.atlasmc.node.inventory.component.AbstractItemComponent;
import de.atlasmc.node.inventory.component.ComponentType;
import de.atlasmc.node.inventory.component.ContainerComponent;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap.Entry;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class CoreContainerComponent extends AbstractItemComponent implements ContainerComponent {
	
	private Int2ObjectMap<ItemStack> slots;
	
	public CoreContainerComponent(ComponentType type) {
		super(type);
	}

	@Override
	public CoreContainerComponent clone() {
		CoreContainerComponent clone = (CoreContainerComponent) super.clone();
		if (hasItems()) {
			clone.slots = new Int2ObjectOpenHashMap<>(slots.size());
			for (Entry<ItemStack> entry : slots.int2ObjectEntrySet()) {
				clone.slots.put(entry.getIntKey(), entry.getValue().clone());
			}
		} else {
			clone.slots = null;
		}
		return clone;
	}

	@Override
	public Int2ObjectMap<ItemStack> getItems() {
		if (slots == null)
			slots = new Int2ObjectOpenHashMap<>();
		return slots;
	}

	@Override
	public boolean hasItems() {
		return slots != null && !slots.isEmpty();
	}

	@Override
	public ItemStack setItem(int slot, ItemStack item) {
		if (item == null && slots != null) {
			return slots.remove(slot);
		}
		return slots.put(slot, item);
	}

	@Override
	public boolean removeItem(ItemStack item) {
		if (!hasItems())
			return false;
		for (Entry<ItemStack> entry : slots.int2ObjectEntrySet()) {
			if (!entry.getValue().equals(item))
				continue;
			return slots.remove(entry.getIntKey(), entry.getValue());
		}
		return false;
	}

	@Override
	public boolean removeItem(ItemType type) {
		if (!hasItems())
			return false;
		for (int i = 0; i < slots.size(); i++) {
			ItemStack item = slots.get(i);
			if (item.getType() != type)
				continue;
			slots.remove(i);
			return true;
		}
		return false;
	}

	@Override
	public ItemStack removeItem(int slot) {
		return slots != null ? slots.remove(slot) : null;
	}

}
