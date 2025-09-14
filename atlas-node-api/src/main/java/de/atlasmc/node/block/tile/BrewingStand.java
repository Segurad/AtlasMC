package de.atlasmc.node.block.tile;

import de.atlasmc.node.inventory.BrewingInventory;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface BrewingStand extends AbstractContainerTile<BrewingInventory> {
	
	public static final NBTSerializationHandler<BrewingStand>
	NBT_HANDLER = NBTSerializationHandler
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
	default NBTSerializationHandler<? extends BrewingStand> getNBTHandler() {
		return NBT_HANDLER;
	}

}
