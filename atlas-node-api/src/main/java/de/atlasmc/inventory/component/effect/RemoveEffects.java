package de.atlasmc.inventory.component.effect;

import de.atlasmc.potion.PotionEffectType;
import de.atlasmc.registry.Registries;
import de.atlasmc.util.dataset.DataSet;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface RemoveEffects extends ComponentEffect {
	
	public static final NBTSerializationHandler<RemoveEffects>
	NBT_HANDLER = NBTSerializationHandler
					.builder(RemoveEffects.class)
					.include(ComponentEffect.NBT_HANDLER)
					.dataSetField("effects", RemoveEffects::getEffects, RemoveEffects::setEffects, Registries.getRegistry(PotionEffectType.class))
					.build();

	DataSet<PotionEffectType> getEffects();
	
	void setEffects(DataSet<PotionEffectType> effects);
	
	boolean hasEffects();
	
	RemoveEffects clone();
	
	@Override
	default NBTSerializationHandler<? extends ComponentEffect> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
