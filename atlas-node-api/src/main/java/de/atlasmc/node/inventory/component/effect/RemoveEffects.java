package de.atlasmc.node.inventory.component.effect;

import de.atlasmc.node.potion.PotionEffectType;
import de.atlasmc.registry.Registries;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface RemoveEffects extends ComponentEffect {
	
	public static final NBTCodec<RemoveEffects>
	NBT_HANDLER = NBTCodec
					.builder(RemoveEffects.class)
					.include(ComponentEffect.NBT_HANDLER)
					.dataSetField("effects", RemoveEffects::getEffects, RemoveEffects::setEffects, Registries.getRegistry(PotionEffectType.class))
					.build();

	DataSet<PotionEffectType> getEffects();
	
	void setEffects(DataSet<PotionEffectType> effects);
	
	boolean hasEffects();
	
	RemoveEffects clone();
	
	@Override
	default NBTCodec<? extends ComponentEffect> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
