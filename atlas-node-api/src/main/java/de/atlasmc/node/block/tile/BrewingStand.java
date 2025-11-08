package de.atlasmc.node.block.tile;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.BrewingInventory;

public interface BrewingStand extends AbstractContainerTile<BrewingInventory> {
	
	public static final NBTCodec<BrewingStand>
	NBT_HANDLER = NBTCodec
					.builder(BrewingStand.class)
					.include(AbstractContainerTile.NBT_HANDLER)
					.shortField("BrewTime", BrewingStand::getBrewTime, BrewingStand::setBrewTime)
					.byteField("Fuel", BrewingStand::getFuelLevel, BrewingStand::setFuelLevel)
					.build();
	
	/**
	 * Returns the remaining fuel level
	 * @return fuel
	 */
	int getFuelLevel();
	
	void setFuelLevel(int value);
	
	/**
	 * The time in ticks until the brewing cycle ends
	 * @return ticks
	 */
	int getBrewTime();
	
	void setBrewTime(int value);
	
	@Override
	default NBTCodec<? extends BrewingStand> getNBTCodec() {
		return NBT_HANDLER;
	}

}
