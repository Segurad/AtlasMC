package de.atlasmc.node.block.tile;

import de.atlasmc.node.inventory.BeaconInventory;
import de.atlasmc.node.potion.PotionEffectType;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Beacon extends AbstractContainerTile<BeaconInventory> {
	
	public static final NBTCodec<Beacon>
	NBT_HANDLER = NBTCodec
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
	default NBTCodec<? extends Beacon> getNBTCodec() {
		return NBT_HANDLER;
	}

}
