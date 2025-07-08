package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface DamageComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:damage");
	
	public static NBTSerializationHandler<DamageComponent> NBT_HANDLER = NBTSerializationHandler.builder(DamageComponent.class)
			.intField(COMPONENT_KEY.toString(), DamageComponent::getDamage, DamageComponent::setDamage)
			.build();
	
	int getDamage();
	
	void setDamage(int damage);
	
	DamageComponent clone();
	
	@Override
	default NBTSerializationHandler<DamageComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
