package de.atlasmc.node.inventory.component;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface MaxDamageComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<MaxDamageComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(MaxDamageComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.intField(ComponentType.MAX_DAMAGE.getNamespacedKey(), MaxDamageComponent::getMaxDamage, MaxDamageComponent::setMaxDamage, 0)
					.build();
	
	int getMaxDamage();
	
	void setMaxDamage(int damage);
	
	MaxDamageComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends MaxDamageComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
