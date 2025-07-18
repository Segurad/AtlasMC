package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface BundleContentsComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:bundle_contents");
	
	public static final NBTSerializationHandler<BundleContentsComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(BundleContentsComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.typeList(COMPONENT_KEY, BundleContentsComponent::hasItems, BundleContentsComponent::getItems, ItemStack.NBT_HANDLER)
					.build();
	
	List<ItemStack> getItems();
	
	boolean hasItems();
	
	void addItem(ItemStack item);
	
	void removeItem(ItemStack item);
	
	BundleContentsComponent clone();

	@Override
	default NBTSerializationHandler<? extends BundleContentsComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
