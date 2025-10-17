package de.atlasmc.node.inventory.component;

import de.atlasmc.node.entity.DamageType;
import de.atlasmc.tag.TagKey;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface DamageResistantComponent extends ItemComponent {
	
	public static final NBTCodec<DamageResistantComponent>
	NBT_HANDLER = NBTCodec
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
	default NBTCodec<? extends DamageResistantComponent> getNBTCodec() {
		return NBT_HANDLER;
	}

}
