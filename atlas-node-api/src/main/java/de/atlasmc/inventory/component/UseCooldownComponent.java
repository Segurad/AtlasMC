package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface UseCooldownComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<UseCooldownComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(UseCooldownComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(ComponentType.USE_COOLDOWN.getNamespacedKey())
					.floatField("seconds", UseCooldownComponent::getSeconds, UseCooldownComponent::setSeconds)
					.namespacedKey("cooldown_group", UseCooldownComponent::getGroup, UseCooldownComponent::setGroup)
					.endComponent()
					.build();
					
	
	float getSeconds();
	
	void setSeconds(float seconds);
	
	NamespacedKey getGroup();
	
	void setGroup(NamespacedKey group);
	
	UseCooldownComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends ItemComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
