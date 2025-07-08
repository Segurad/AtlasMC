package de.atlasmc.inventory.component;

import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface ChargedProjectilesComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:charged_projectiles");
	
	public static final NBTSerializationHandler<ChargedProjectilesComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(ChargedProjectilesComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.typeList(COMPONENT_KEY, ChargedProjectilesComponent::hasProjectiles, ChargedProjectilesComponent::getProjectiles, ItemStack.NBT_HANDLER)
					.build();
	
	List<ItemStack> getProjectiles();
	
	boolean hasProjectiles();
	
	void addProjectile(ItemStack item);
	
	void removeProjectile(ItemStack item);
	
	ChargedProjectilesComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends ChargedProjectilesComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
