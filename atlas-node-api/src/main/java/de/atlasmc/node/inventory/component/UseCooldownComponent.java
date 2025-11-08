package de.atlasmc.node.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.nbt.codec.NBTCodec;

public interface UseCooldownComponent extends ItemComponent {
	
	public static final NBTCodec<UseCooldownComponent>
	NBT_HANDLER = NBTCodec
					.builder(UseCooldownComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.beginComponent(ComponentType.USE_COOLDOWN.getNamespacedKey())
					.floatField("seconds", UseCooldownComponent::getSeconds, UseCooldownComponent::setSeconds)
					.codec("cooldown_group", UseCooldownComponent::getGroup, UseCooldownComponent::setGroup, NamespacedKey.NBT_CODEC)
					.endComponent()
					.build();
					
	
	float getSeconds();
	
	void setSeconds(float seconds);
	
	NamespacedKey getGroup();
	
	void setGroup(NamespacedKey group);
	
	UseCooldownComponent clone();
	
	@Override
	default NBTCodec<? extends ItemComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
