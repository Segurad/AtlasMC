package de.atlasmc.node.entity;

import de.atlasmc.node.inventory.PocketHolder;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Villager extends AbstractVillager, Breedable, PocketHolder {
	
	public static final NBTCodec<Villager>
	NBT_HANDLER = NBTCodec
					.builder(Villager.class)
					.include(Breedable.NBT_HANDLER)
					.include(AbstractVillager.NBT_HANDLER)
					.include(PocketHolder.NBT_HANDLER)
					.longField("LastRestock", Villager::getLastRestock, Villager::setLastRestock, 0)
					//.longField("LastGossipDecay", null, null, 0)
					.intField("RestocksToday", Villager::getRestocksToday, Villager::setRestocksToday, 0)
					.boolField("Willing", Villager::isWilling, Villager::setWilling, false)
					.build();
	
	
	
	/**
	 * Sets the tick this Villager was last restocked
	 * @param tick
	 */
	void setLastRestock(long tick);

	/**
	 * Returns the tick this Villager was last restocked
	 * @return
	 */
	long getLastRestock();
	
	int getRestocksToday();

	void setRestocksToday(int restocks);
	
	/**
	 * Returns whether or not this Villager is ready for breeding.<br>
	 * @return true if ready
	 */
	boolean isWilling();
	
	/**
	 * Sets whether or not this Villager is ready for breeding.<br>
	 * This value does not reset over time like {@link Breedable#getInLove()}
	 * @param willing
	 */
	void setWilling(boolean willing);
	
}
