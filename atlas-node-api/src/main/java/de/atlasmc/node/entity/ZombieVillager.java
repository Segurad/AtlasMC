package de.atlasmc.node.entity;

import java.util.UUID;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface ZombieVillager extends Zombie, AbstractVillager {
	
	public static final NBTCodec<ZombieVillager>
	NBT_HANDLER = NBTCodec
					.builder(ZombieVillager.class)
					.include(Zombie.NBT_HANDLER)
					.include(AbstractVillager.NBT_HANDLER)
					.intField("ConversionTime", ZombieVillager::getConversionTime, ZombieVillager::setConversionTime, -1)
					.uuid("ConversionPlayer", ZombieVillager::getConversionPlayer, ZombieVillager::setConversionPlayer)
					.boolField("IsConverting", ZombieVillager::isConverting, ZombieVillager::setConverting, false)
					.build();
	
	boolean isConverting();

	void setConversionPlayer(UUID uuid);
	
	UUID getConversionPlayer();

	void setConversionTime(int ticks);
	
	/**
	 * Returns the number of ticks it takes until this {@link ZombieVillager} is converted to a {@link Villager}.<br>
	 * Will be -1 if no conversion is happening
	 * @return ticks or -1
	 */
	int getConversionTime();

	void setConverting(boolean converting);
	
}
