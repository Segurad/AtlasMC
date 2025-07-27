package de.atlasmc.inventory.component;

import de.atlasmc.FireworkExplosion;
import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface FireworkExplosionComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:firework_explosion");
	
	public static final NBTSerializationHandler<FireworkExplosionComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(FireworkExplosionComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.typeCompoundField(COMPONENT_KEY, FireworkExplosionComponent::getExplosion, FireworkExplosionComponent::setExplosion, FireworkExplosion.NBT_HANDLER)
					.build();
	
	FireworkExplosion getExplosion();
	
	void setExplosion(FireworkExplosion effect);
	
	FireworkExplosionComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends FireworkExplosionComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
