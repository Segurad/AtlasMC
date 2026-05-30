package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.PocketHolder;
import de.atlasmc.util.annotation.NotNull;

public interface WanderingTrader extends Breedable, Merchant, PocketHolder {
	
	@NotNull
	public static final NBTCodec<WanderingTrader>
	NBT_CODEC = NBTCodec
					.builder(WanderingTrader.class)
					.include(Breedable.NBT_CODEC)
					.include(Merchant.NBT_CODEC)
					.intField("DespawnDelay", WanderingTrader::getDespawnDelay, WanderingTrader::setDespawnDelay, 0)
					// wander target
					.include(PocketHolder.NBT_CODEC)
					.build();

	void setDespawnDelay(int delay);

	/**
	 * Returns the time in ticks until this WanderingTrader will despawn or -1 if the Trader will not despawn by time
	 * @return ticks or -1
	 */
	int getDespawnDelay();
	
	@Override
	default NBTCodec<? extends WanderingTrader> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
