package de.atlasmc.node.inventory.component;

import de.atlasmc.Color;
import de.atlasmc.chat.Chat;
import de.atlasmc.node.potion.PotionData;
import de.atlasmc.node.potion.PotionEffect;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface PotionContentsComponent extends AbstractPotionEffectComponent {
	
	public static final NBTSerializationHandler<PotionContentsComponent>
	NBT_HANDLER = NBTSerializationHandler
					.builder(PotionContentsComponent.class)
					.include(AbstractPotionEffectComponent.NBT_HANDLER)
					.beginComponent(ComponentType.POTION_CONTENTS.getNamespacedKey(), PotionContentsComponent::hasCustomData)
					.registryValue("potion", PotionContentsComponent::getPotionData, PotionContentsComponent::setPotionData, PotionData.REGISTRY_KEY)
					.color("custom_color", PotionContentsComponent::getCustomColor, PotionContentsComponent::setCustomColor)
					.chat("custom_name", PotionContentsComponent::getCustomName, PotionContentsComponent::setCustomName)
					.typeList("custom_effects", PotionContentsComponent::hasEffects, PotionContentsComponent::getEffects, PotionEffect.NBT_HANDLER)
					.endComponent()
					.registryValue(ComponentType.POTION_CONTENTS.getNamespacedKey(), PotionContentsComponent::getPotionData, PotionContentsComponent::setPotionData, PotionData.REGISTRY_KEY)
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
	default NBTSerializationHandler<? extends PotionContentsComponent> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
