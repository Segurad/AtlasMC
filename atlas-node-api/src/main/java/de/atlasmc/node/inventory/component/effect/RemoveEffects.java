package de.atlasmc.node.inventory.component.effect;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.node.potion.PotionEffectType;
import de.atlasmc.registry.Registries;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface RemoveEffects extends ComponentEffect {
	
	public static final NBTCodec<RemoveEffects>
	NBT_CODEC = NBTCodec
					.builder(RemoveEffects.class)
					.include(ComponentEffect.NBT_HANDLER)
					.dataSetField("effects", RemoveEffects::getEffects, RemoveEffects::setEffects, Registries.getRegistry(PotionEffectType.class))
					.build();
	
	public static final StreamCodec<RemoveEffects>
	STREAM_CODEC = StreamCodec
					.builder(RemoveEffects.class)
					.include(RemoveEffects.STREAM_CODEC)
					.dataSet(RemoveEffects::getEffects, RemoveEffects::setEffects, PotionEffectType.REGISTRY_KEY)
					.build();

	DataSet<PotionEffectType> getEffects();
	
	void setEffects(DataSet<PotionEffectType> effects);
	
	boolean hasEffects();
	
	RemoveEffects clone();
	
	@Override
	default NBTCodec<? extends RemoveEffects> getNBTCodec() {
		return NBT_CODEC;
	}
	
	@Override
	default StreamCodec<? extends RemoveEffects> getStreamCodec() {
		return STREAM_CODEC;
	}
	
}
