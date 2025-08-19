package de.atlasmc.inventory.component;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface WeaponComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<WeaponComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(WeaponComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(ComponentType.WEAPON)
					.intField("item_damage_per_attack", WeaponComponent::getItemDamagePerAttack, WeaponComponent::setItemDamagePerAttack, 0)
					.floatField("disable_blocking_for_seconds", WeaponComponent::getDisableBlockSeconds, WeaponComponent::setDisableBlockSeconds, 0)
					.endComponent()
					.build();
	
	int getItemDamagePerAttack();
	
	void setItemDamagePerAttack(int damage);
	
	float getDisableBlockSeconds();
	
	void setDisableBlockSeconds(float disable);
	
	WeaponComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends WeaponComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
