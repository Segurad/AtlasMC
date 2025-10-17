package de.atlasmc.node.entity;

import de.atlasmc.node.inventory.PocketHolder;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface WanderingTrader extends Breedable, Merchant, PocketHolder {
	
	public static final NBTCodec<WanderingTrader>
	NBT_HANDLER = NBTCodec
					.builder(WanderingTrader.class)
					.include(Breedable.NBT_HANDLER)
					.include(Merchant.NBT_HANDLER)
					.intField("DespawnDelay", WanderingTrader::getDespawnDelay, WanderingTrader::setDespawnDelay, 0)
					// wander target
					.include(PocketHolder.NBT_HANDLER)
					.build();

	void setDespawnDelay(int delay);

	/**
	 * Returns the time in ticks until this WanderingTrader will despawn or -1 if the Trader will not despawn by time
	 * @return ticks or -1
	 */
	int getDespawnDelay();
	
	@Override
	default NBTCodec<? extends WanderingTrader> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
