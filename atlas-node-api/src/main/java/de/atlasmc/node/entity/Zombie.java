package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Zombie extends Monster {
	
	public static final NBTCodec<Zombie>
	NBT_HANDLER = NBTCodec
					.builder(Zombie.class)
					.include(Monster.NBT_HANDLER)
					.boolField("CanBreakDoors", Zombie::canBreakDoors, Zombie::setCanBreakDoors, false)
					.intField("DrownedConverionTime", Zombie::getDrownedConverionTime, Zombie::setDrownedConversionTime, -1)
					//.intField("InWaterTime", null, null)
					.boolField("IsBaby", Zombie::isBaby, Zombie::setBaby, false)
					.boolField("IsBecomingDrowned", Zombie::isBecomingDrowned, Zombie::setBecomingDorwned, false) // non standard
					.build();
	
	boolean isBaby();
	
	boolean isBecomingDrowned();

	void setBaby(boolean baby);
	
	void setBecomingDorwned(boolean drowned);

	void setCanBreakDoors(boolean breakDoor);
	
	boolean canBreakDoors();

	void setDrownedConversionTime(int ticks);
	
	/**
	 * Returns the ticks in which this Zombie will be converted to a Drowned.<br>
	 * Will be -1 if no conversion is happening
	 * @return ticks or -1
	 */
	int getDrownedConverionTime();
	
	@Override
	default NBTCodec<? extends Zombie> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
