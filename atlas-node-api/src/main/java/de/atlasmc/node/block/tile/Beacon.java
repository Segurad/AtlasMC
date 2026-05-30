package de.atlasmc.node.block.tile;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.BeaconInventory;
import de.atlasmc.node.potion.PotionEffectType;
import de.atlasmc.registry.Registries;
import de.atlasmc.util.annotation.NotNull;

public interface Beacon extends AbstractContainerTile<BeaconInventory> {
	
	@NotNull
	public static final NBTCodec<Beacon>
	NBT_CODEC = NBTCodec
					.builder(Beacon.class)
					.include(AbstractContainerTile.NBT_CODEC)
					.codec("primary_effect", Beacon::getPrimaryEffectType, Beacon::setPrimaryEffectType, Registries.registryValueNBTCodec(PotionEffectType.REGISTRY_KEY))
					.codec("secondary_effect", Beacon::getSecondaryEffectType, Beacon::setSecondaryEffectType, Registries.registryValueNBTCodec(PotionEffectType.REGISTRY_KEY))
					.build();
	
	PotionEffectType getPrimaryEffectType();
	
	void setPrimaryEffectType(PotionEffectType primary);
	
	PotionEffectType getSecondaryEffectType();
	
	void setSecondaryEffectType(PotionEffectType secondary);
	
	@Override
	default NBTCodec<? extends Beacon> getNBTCodec() {
		return NBT_CODEC;
	}

}
