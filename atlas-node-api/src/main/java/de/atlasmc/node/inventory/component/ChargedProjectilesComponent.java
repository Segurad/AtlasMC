package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.ItemStack;

public interface ChargedProjectilesComponent extends ItemComponent {
	
	public static final NBTCodec<ChargedProjectilesComponent>
	NBT_HANDLER = NBTCodec
					.builder(ChargedProjectilesComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.codecList(ComponentType.CHARGED_PROJECTILES.getNamespacedKey(), ChargedProjectilesComponent::hasProjectiles, ChargedProjectilesComponent::getProjectiles, ItemStack.NBT_HANDLER)
					.build();
	
	public static final StreamCodec<ChargedProjectilesComponent>
	STREAM_CODEC = StreamCodec
					.builder(ChargedProjectilesComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.listCodec(ChargedProjectilesComponent::hasProjectiles, ChargedProjectilesComponent::getProjectiles, ItemStack.STREAM_CODEC)
					.build();
	
	List<ItemStack> getProjectiles();
	
	boolean hasProjectiles();
	
	void addProjectile(ItemStack item);
	
	void removeProjectile(ItemStack item);
	
	ChargedProjectilesComponent clone();
	
	@Override
	default NBTCodec<? extends ChargedProjectilesComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
	@Override
	default StreamCodec<? extends ChargedProjectilesComponent> getStreamCodec() {
		return STREAM_CODEC;
	}

}
