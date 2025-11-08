package de.atlasmc.node.inventory.component;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.entity.DamageType;
import de.atlasmc.tag.TagKey;

public interface DamageResistantComponent extends ItemComponent {
	
	public static final NBTCodec<DamageResistantComponent>
	NBT_CODEC = NBTCodec
					.builder(DamageResistantComponent.class)
					.include(ItemComponent.NBT_CODEC)
					.beginComponent(ComponentType.DAMAGE_RESISTANT.getNamespacedKey())
					.codec("types", DamageResistantComponent::getDamageTypes, DamageResistantComponent::setDamageTypes, TagKey.NBT_CODEC)
					.endComponent()
					.build();
	
	public static final StreamCodec<DamageResistantComponent>
	STREAM_CODEC = StreamCodec
					.builder(DamageResistantComponent.class)
					.include(ItemComponent.STREAM_CODEC)
					.tagKey(DamageResistantComponent::getDamageTypes, DamageResistantComponent::setDamageTypes)
					.build();
	
	TagKey<DamageType> getDamageTypes();
	
	void setDamageTypes(TagKey<DamageType> types);
	
	DamageResistantComponent clone();
	
	@Override
	default NBTCodec<? extends DamageResistantComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends DamageResistantComponent> getStreamCodec() {
		return STREAM_CODEC;
	}

}
