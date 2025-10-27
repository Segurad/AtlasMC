package de.atlasmc.node.inventory.component;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface WeaponComponent extends ItemComponent {
	
	public static final NBTCodec<WeaponComponent>
	NBT_HANDLER = NBTCodec
					.builder(WeaponComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.beginComponent(ComponentType.WEAPON.getNamespacedKey())
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
	default NBTCodec<? extends WeaponComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
