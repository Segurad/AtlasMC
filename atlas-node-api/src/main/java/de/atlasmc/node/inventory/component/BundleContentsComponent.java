package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.ItemStack;

public interface BundleContentsComponent extends ItemComponent {
	
	public static final NBTCodec<BundleContentsComponent>
	NBT_HANDLER = NBTCodec
					.builder(BundleContentsComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codecList(ComponentType.BUNDLE_CONTENTS.getNamespacedKey(), BundleContentsComponent::hasItems, BundleContentsComponent::getItems, ItemStack.NBT_HANDLER)
					.build();
	
	public static final StreamCodec<BundleContentsComponent>
	STREAM_CODEC = StreamCodec
					.builder(BundleContentsComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.listCodec(BundleContentsComponent::hasItems, BundleContentsComponent::getItems, ItemStack.STREAM_CODEC)
					.build();
	
	List<ItemStack> getItems();
	
	boolean hasItems();
	
	void addItem(ItemStack item);
	
	boolean removeItem(ItemStack item);
	
	BundleContentsComponent clone();

	@Override
	default NBTCodec<? extends BundleContentsComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	@Override
	default StreamCodec<? extends BundleContentsComponent> getStreamCodec() {
		return STREAM_CODEC;
	}
	
}
