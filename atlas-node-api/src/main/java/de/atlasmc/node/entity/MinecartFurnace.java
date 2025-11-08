package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;

public interface MinecartFurnace extends AbstractMinecart {
	
	public static final NBTCodec<MinecartFurnace>
	NBT_HANDLER = NBTCodec
					.builder(MinecartFurnace.class)
					.include(AbstractMinecart.NBT_HANDLER)
					.shortField("Fuel", MinecartFurnace::getFuelLevel, MinecartFurnace::setFuelLevel)
					.doubleField("PushX", MinecartFurnace::getPushX, MinecartFurnace::setPushX, 0)
					.doubleField("PushZ", MinecartFurnace::getPushZ, MinecartFurnace::setPushZ, 0)
					.build();
	
	boolean hasFuel();
	
	void setFuel(boolean fuel);
	
	void setFuelLevel(int ticks);
	
	/**
	 * Returns the time in tick until this minecart runs out of fuel or -1 if no time is counted
	 * @return ticks or -1
	 */
	int getFuelLevel();
	
	double getPushX();
	
	void setPushX(double x);
	
	double getPushZ();
	
	void setPushZ(double z);

}
