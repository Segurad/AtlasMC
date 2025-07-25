package de.atlasmc.entity;

import de.atlasmc.inventory.PocketHolder;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface WanderingTrader extends Breedable, Merchant, PocketHolder {
	
	public static final NBTSerializationHandler<WanderingTrader>
	NBT_HANDLER = NBTSerializationHandler
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
	default NBTSerializationHandler<? extends WanderingTrader> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
