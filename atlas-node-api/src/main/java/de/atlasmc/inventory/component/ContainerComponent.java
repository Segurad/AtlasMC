package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.inventory.ItemType;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ContainerComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:container");
	
	public static final NBTSerializationHandler<ContainerComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ContainerComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.typeListSearchIntIndexField(COMPONENT_KEY, "slot", ContainerComponent::hasItems, ContainerComponent::getItems, ItemStack.NBT_HANDLER, true)
					.build();
	
	List<ItemStack> getItems();
	
	boolean hasItems();
	
	void addItem(ItemStack item);
	
	void setItem(int slot, ItemStack item);
	
	void removeItem(ItemStack item);
	
	void removeItem(ItemType type);
	
	ContainerComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends ContainerComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
