package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface WeaponComponent extends ItemComponent {
	
	@NotNull
	public static final NBTCodec<WeaponComponent>
	NBT_CODEC = NBTCodec
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
	
	@Override
	WeaponComponent clone();
	
	@Override
	default NBTCodec<? extends WeaponComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
