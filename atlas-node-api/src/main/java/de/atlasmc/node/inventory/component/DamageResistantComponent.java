package de.atlasmc.node.inventory.component;

import de.atlasmc.node.entity.DamageType;
import de.atlasmc.tag.TagKey;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface DamageResistantComponent extends ItemComponent {
	
	public static final NBTSerializationHandler<DamageResistantComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(DamageResistantComponent.class)
					.include(ItemComponent.NBT_HANDLER)
					.beginComponent(ComponentType.DAMAGE_RESISTANT.getNamespacedKey())
					.tagField("types", DamageResistantComponent::getDamageTypes, DamageResistantComponent::setDamageTypes)
					.endComponent()
					.build();
	
	TagKey<DamageType> getDamageTypes();
	
	void setDamageTypes(TagKey<DamageType> types);
	
	DamageResistantComponent clone();
	
	@Override
	default NBTSerializationHandler<? extends DamageResistantComponent> getNBTHandler() {
		return NBT_HANDLER;
	}

}
