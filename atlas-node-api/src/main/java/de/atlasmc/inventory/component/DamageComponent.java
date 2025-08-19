package de.atlasmc.inventory.component;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface DamageComponent extends ItemComponent {
	
	public static NBTSerializationHandler<DamageComponent> 
	NBT_HANDLER = NBTSerializationHandler
			.builder(DamageComponent.class)
			.include(ItemComponent.NBT_HANDLER)
			.intField(ComponentType.DAMAGE, DamageComponent::getDamage, DamageComponent::setDamage)
			.build();
	
	int getDamage();
	
	void setDamage(int damage);
	
	DamageComponent clone();
	
	@Override
	default NBTSerializationHandler<DamageComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
