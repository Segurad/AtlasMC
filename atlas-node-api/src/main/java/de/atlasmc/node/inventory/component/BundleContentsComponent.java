package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.CodecListFieldCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.util.annotation.NotNull;

public interface BundleContentsComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<BundleContentsComponent>
	NBT_CODEC = CodecListFieldCodec
				.builder(BundleContentsComponent.class, ItemStack.NBT_CODEC)
				.include(ItemComponent.NBT_CODEC)
				.hasData(BundleContentsComponent::hasItems)
				.getter(BundleContentsComponent::getItems)
				.build();
	
	@NotNull
	public static final StreamCodec<BundleContentsComponent>
	STREAM_CODEC = StreamCodec
					.builder(BundleContentsComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.codecList(BundleContentsComponent::hasItems, BundleContentsComponent::getItems, ItemStack.STREAM_CODEC)
					.build();
	
	List<ItemStack> getItems();
	
	boolean hasItems();
	
	void addItem(ItemStack item);
	
	boolean removeItem(ItemStack item);
	
	@Override
	BundleContentsComponent clone();

	@Override
	default NBTCodec<? extends BundleContentsComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends BundleContentsComponent> getStreamCodec() {
		return STREAM_CODEC;
	}
	
}
