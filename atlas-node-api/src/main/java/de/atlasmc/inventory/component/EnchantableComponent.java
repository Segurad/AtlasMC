package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface EnchantableComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:enchantable");
	
	public static final NBTSerializationHandler<EnchantableComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(EnchantableComponent.class)
					.intField(COMPONENT_KEY, EnchantableComponent::getValue, EnchantableComponent::setValue)
					.build();
	
	int getValue();
	
	void setValue(int value);
	
	EnchantableComponent clone();

}
