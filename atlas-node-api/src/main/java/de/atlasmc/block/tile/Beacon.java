package de.atlasmc.block.tile;

import de.atlasmc.inventory.BeaconInventory;
import de.atlasmc.potion.PotionEffectType;
import de.atlasmc.registry.Registries;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Beacon extends AbstractContainerTile<BeaconInventory> {
	
	public static final NBTSerializationHandler<Beacon>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Beacon.class)
					.include(AbstractContainerTile.NBT_HANDLER)
					.registryValue("primary_effect", Beacon::getPrimaryEffectType, Beacon::setPrimaryEffectType, Registries.getRegistry(PotionEffectType.class))
					.registryValue("secondary_effect", Beacon::getSecondaryEffectType, Beacon::setSecondaryEffectType, Registries.getRegistry(PotionEffectType.class))
					.build();
	
	PotionEffectType getPrimaryEffectType();
	
	void setPrimaryEffectType(PotionEffectType primary);
	
	PotionEffectType getSecondaryEffectType();
	
	void setSecondaryEffectType(PotionEffectType secondary);
	
	@Override
	default NBTSerializationHandler<? extends Beacon> getNBTHandler() {
		return NBT_HANDLER;
	}

}
