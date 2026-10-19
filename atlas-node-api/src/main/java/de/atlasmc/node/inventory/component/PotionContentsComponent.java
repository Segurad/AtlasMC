package de.atlasmc.node.inventory.component;

import de.atlasmc.Color;
import de.atlasmc.chat.Chat;
import de.atlasmc.nbt.codec.CodecFieldCodec;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.OrElseCodec;
import de.atlasmc.node.potion.PotionData;
import de.atlasmc.node.potion.PotionEffect;
import de.atlasmc.registry.Registries;
import de.atlasmc.util.annotation.NotNull;

public interface PotionContentsComponent extends AbstractPotionEffectComponent {
	
	@NotNull
	public static final NBTCodec<PotionContentsComponent>
	NBT_CODEC = new OrElseCodec<>(PotionContentsComponent.class,
					NBTCodec
					.builder(PotionContentsComponent.class)
					.include(AbstractPotionEffectComponent.NBT_CODEC)
					.setHasData(PotionContentsComponent::hasCustomData)
					.setAllowNoData(false)
					.codec("potion", PotionContentsComponent::getPotionData, PotionContentsComponent::setPotionData, Registries.registryValueNBTCodec(PotionData.REGISTRY_KEY))
					.codec("custom_color", PotionContentsComponent::getCustomColor, PotionContentsComponent::setCustomColor, Color.NBT_CODEC)
					.codec("custom_name", PotionContentsComponent::getCustomName, PotionContentsComponent::setCustomName, Chat.NBT_CODEC)
					.codecList("custom_effects", PotionContentsComponent::hasEffects, PotionContentsComponent::getEffects, PotionEffect.NBT_CODEC)
					.build(),
					CodecFieldCodec
					.builder(PotionContentsComponent.class, Registries.registryValueNBTCodec(PotionData.REGISTRY_KEY))
					.getter(PotionContentsComponent::getPotionData)
					.setter(PotionContentsComponent::setPotionData)
					.build());
	
	PotionData getPotionData();
	
	void setPotionData(PotionData data);
	
	boolean hasCustomData();
	
	Color getCustomColor();
	
	void setCustomColor(Color color);
	
	Chat getCustomName();
	
	void setCustomName(Chat name);
	
	@Override
	PotionContentsComponent clone();

	@Override
	default NBTCodec<? extends PotionContentsComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
