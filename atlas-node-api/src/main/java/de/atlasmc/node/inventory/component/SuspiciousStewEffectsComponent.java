package de.atlasmc.node.inventory.component;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.CodecListFieldCodec;
import de.atlasmc.node.potion.PotionEffect;
import de.atlasmc.util.annotation.NotNull;

public interface SuspiciousStewEffectsComponent extends AbstractPotionEffectComponent {
	
	@NotNull
	public static final NBTCodec<SuspiciousStewEffectsComponent>
	NBT_CODEC = CodecListFieldCodec
			.builder(SuspiciousStewEffectsComponent.class, PotionEffect.NBT_CODEC)
			.include(AbstractPotionEffectComponent.NBT_CODEC)
			.hasData(SuspiciousStewEffectsComponent::hasEffects)
			.getter(SuspiciousStewEffectsComponent::getEffects)
			.build();
	
	@Override
	SuspiciousStewEffectsComponent clone();
	
	@Override
	default NBTCodec<? extends SuspiciousStewEffectsComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
