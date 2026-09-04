package de.atlasmc.node.entity.component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import de.atlasmc.component.Component;
import de.atlasmc.component.ComponentType;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.PocketHolder;
import de.atlasmc.util.annotation.NotNull;

public class PocketComponent implements Component {
	
	@NotNull
	public static final NBTCodec<PocketHolder>
	NBT_CODEC = NBTCodec
					.builder(PocketHolder.class)
					.codecList("Inventory", PocketHolder::hasPocketItems, PocketHolder::getPocketItems, ItemStack.NBT_CODEC)
					.build();
	
	private final ComponentType type;
	private List<ItemStack> items;
	
	public PocketComponent(ComponentType type) {
		this.type = Objects.requireNonNull(type, "type");
	}
	
	@NotNull
	public List<ItemStack> getItems() {
		var items = this.items;
		if (items == null) {
			this.items = items = new ArrayList<>();
		}
		return items;
	}
	
	public boolean hasItems() {
		return items != null && !items.isEmpty();
	}
	
	public void addItem(ItemStack item) {
		Objects.requireNonNull(item, "item");
		getItems().add(item);
	}
	
	public void removePocketItem(ItemStack item) {
		if (item == null || !hasItems())
			return;
		getItems().remove(item);
	}

	@Override
	public ComponentType getType() {
		return type;
	}

}
