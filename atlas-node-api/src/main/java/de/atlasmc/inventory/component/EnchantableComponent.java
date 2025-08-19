package de.atlasmc.inventory.component;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface EnchantableComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<EnchantableComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(EnchantableComponent.class)
					.intField(ComponentType.ENCHANTABLE, EnchantableComponent::getValue, EnchantableComponent::setValue)
					.build();
	
	int getValue();
	
	void setValue(int value);
	
	EnchantableComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends ItemComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
