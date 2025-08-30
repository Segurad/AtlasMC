package de.atlasmc.block.tile;

import de.atlasmc.inventory.BeaconInventory;
import de.atlasmc.potion.PotionEffectType;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Beacon extends AbstractContainerTile<BeaconInventory> {
	
	public static final NBTSerializationHandler<Beacon>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Beacon.class)
					.include(AbstractContainerTile.NBT_HANDLER)
					.registryValue("primary_effect", Beacon::getPrimaryEffectType, Beacon::setPrimaryEffectType, PotionEffectType.REGISTRY_KEY)
					.registryValue("secondary_effect", Beacon::getSecondaryEffectType, Beacon::setSecondaryEffectType, PotionEffectType.REGISTRY_KEY)
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
