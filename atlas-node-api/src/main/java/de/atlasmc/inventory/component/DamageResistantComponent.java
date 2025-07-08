package de.atlasmc.inventory.component;

import de.atlasmc.NamespacedKey;
import de.atlasmc.entity.DamageType;
import de.atlasmc.tag.Tag;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface DamageResistantComponent extends ItemComponent {
	
	public static final NamespacedKey COMPONENT_KEY = NamespacedKey.literal("minecraft:damage_resistant");
	
	public static final NBTSerializationHandler<DamageResistantComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(DamageResistantComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(COMPONENT_KEY)
					.tagField("types", DamageResistantComponent::getDamageTypes, DamageResistantComponent::setDamageTypes)
					.endComponent()
					.build();
	
	Tag<DamageType> getDamageTypes();
	
	void setDamageTypes(Tag<DamageType> types);
	
	DamageResistantComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends DamageResistantComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
