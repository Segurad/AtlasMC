package de.atlasmc.node.inventory.component;

import de.atlasmc.Color;
import de.atlasmc.chat.Chat;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.potion.PotionData;
import de.atlasmc.node.potion.PotionEffect;
import de.atlasmc.registry.Registries;

public interface PotionContentsComponent extends AbstractPotionEffectComponent {
	
	public static final NBTCodec<PotionContentsComponent>
	NBT_HANDLER = NBTCodec
					.builder(PotionContentsComponent.class)
					.include(AbstractPotionEffectComponent.NBT_CODEC)
					.beginComponent(ComponentType.POTION_CONTENTS.getNamespacedKey(), PotionContentsComponent::hasCustomData)
					.codec("potion", PotionContentsComponent::getPotionData, PotionContentsComponent::setPotionData, Registries.registryValueNBTCodec(PotionData.REGISTRY_KEY))
					.codec("custom_color", PotionContentsComponent::getCustomColor, PotionContentsComponent::setCustomColor, Color.NBT_CODEC)
					.codec("custom_name", PotionContentsComponent::getCustomName, PotionContentsComponent::setCustomName, Chat.NBT_CODEC)
					.codecList("custom_effects", PotionContentsComponent::hasEffects, PotionContentsComponent::getEffects, PotionEffect.NBT_CODEC)
					.endComponent()
					.codec(ComponentType.POTION_CONTENTS.getNamespacedKey(), PotionContentsComponent::getPotionData, PotionContentsComponent::setPotionData, Registries.registryValueNBTCodec(PotionData.REGISTRY_KEY))
					.build();
	
	PotionData getPotionData();
	
	void setPotionData(PotionData data);
	
	boolean hasCustomData();
	
	Color getCustomColor();
	
	void setCustomColor(Color color);
	
	Chat getCustomName();
	
	void setCustomName(Chat name);
	
	PotionContentsComponent clone();

	@Override
	default NBTCodec<? extends PotionContentsComponent> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
