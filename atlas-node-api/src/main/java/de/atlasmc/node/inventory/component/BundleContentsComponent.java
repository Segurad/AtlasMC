package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface BundleContentsComponent extends ItemComponent {
	
	public static final NBTCodec<BundleContentsComponent>
	NBT_HANDLER = NBTCodec
					.builder(BundleContentsComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.typeList(ComponentType.BUNDLE_CONTENTS.getNamespacedKey(), BundleContentsComponent::hasItems, BundleContentsComponent::getItems, ItemStack.NBT_HANDLER)
					.build();
	
	List<ItemStack> getItems();
	
	boolean hasItems();
	
	void addItem(ItemStack item);
	
	void removeItem(ItemStack item);
	
	BundleContentsComponent clone();

	@Override
	default NBTCodec<? extends BundleContentsComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
