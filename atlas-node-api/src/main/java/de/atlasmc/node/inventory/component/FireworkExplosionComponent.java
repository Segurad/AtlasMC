package de.atlasmc.node.inventory.component;

import de.atlasmc.node.FireworkExplosion;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface FireworkExplosionComponent extends ItemComponent {
	
	public static final NBTCodec<FireworkExplosionComponent>
	NBT_HANDLER = NBTCodec
					.builder(FireworkExplosionComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.typeCompoundField(ComponentType.FIREWORK_EXPLOSION.getNamespacedKey(), FireworkExplosionComponent::getExplosion, FireworkExplosionComponent::setExplosion, FireworkExplosion.NBT_HANDLER)
					.build();
	
	FireworkExplosion getExplosion();
	
	void setExplosion(FireworkExplosion effect);
	
	FireworkExplosionComponent clone();
	
	@Override
	default NBTCodec<? extends FireworkExplosionComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
