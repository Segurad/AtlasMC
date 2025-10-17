package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.inventory.ItemType;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface ContainerComponent extends ItemComponent {
	
	public static final NBTCodec<ContainerComponent>
	NBT_HANDLER = NBTCodec
					.builder(ContainerComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.typeListSearchIntIndexField(ComponentType.CONTAINER.getNamespacedKey(), "slot", ContainerComponent::hasItems, ContainerComponent::getItems, ItemStack.NBT_HANDLER, true)
					.build();
	
	List<ItemStack> getItems();
	
	boolean hasItems();
	
	void addItem(ItemStack item);
	
	void setItem(int slot, ItemStack item);
	
	void removeItem(ItemStack item);
	
	void removeItem(ItemType type);
	
	ContainerComponent clone();
	
	@Override
	default NBTCodec<? extends ContainerComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
