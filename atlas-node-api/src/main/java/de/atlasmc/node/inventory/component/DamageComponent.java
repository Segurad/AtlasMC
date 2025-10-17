package de.atlasmc.node.inventory.component;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface DamageComponent extends ItemComponent {
	
	public static NBTCodec<DamageComponent> 
	NBT_HANDLER = NBTCodec
			.builder(DamageComponent.class)
			.include(ItemComponent.NBT_HANDLER)
			.intField(ComponentType.DAMAGE.getNamespacedKey(), DamageComponent::getDamage, DamageComponent::setDamage)
			.build();
	
	int getDamage();
	
	void setDamage(int damage);
	
	DamageComponent clone();
	
	@Override
	default NBTCodec<DamageComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
