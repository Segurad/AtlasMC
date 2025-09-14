package de.atlasmc.node.inventory.component;

import de.atlasmc.node.FireworkExplosion;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface FireworkExplosionComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<FireworkExplosionComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(FireworkExplosionComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.typeCompoundField(ComponentType.FIREWORK_EXPLOSION.getNamespacedKey(), FireworkExplosionComponent::getExplosion, FireworkExplosionComponent::setExplosion, FireworkExplosion.NBT_HANDLER)
					.build();
	
	FireworkExplosion getExplosion();
	
	void setExplosion(FireworkExplosion effect);
	
	FireworkExplosionComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends FireworkExplosionComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
