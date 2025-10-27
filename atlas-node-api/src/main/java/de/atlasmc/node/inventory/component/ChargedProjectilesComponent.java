package de.atlasmc.node.inventory.component;

import java.util.List;

import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface ChargedProjectilesComponent extends ItemComponent {
	
	public static final NBTCodec<ChargedProjectilesComponent>
	NBT_HANDLER = NBTCodec
					.builder(ChargedProjectilesComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.typeList(ComponentType.CHARGED_PROJECTILES.getNamespacedKey(), ChargedProjectilesComponent::hasProjectiles, ChargedProjectilesComponent::getProjectiles, ItemStack.NBT_HANDLER)
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

}
