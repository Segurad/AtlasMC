package de.atlasmc.block.tile;

import de.atlasmc.inventory.AbstractFurnaceInventory;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Furnace extends AbstractContainerTile<AbstractFurnaceInventory> {

	public static final NBTSerializationHandler<Furnace>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Furnace.class)
					.include(AbstractContainerTile.NBT_HANDLER)
					.shortField("lit_time_remaining", Furnace::getFuelLevel, Furnace::setFuelLevel, (short) 0)
					.shortField("cooking_time_spend", Furnace::getProgress, Furnace::setProgress, (short) 0)
					.shortField("cooking_time_total", Furnace::getMaxProgress, Furnace::setMaxProgress, (short) 0)
					.shortField("lit_time_total", Furnace::getMaxFuelLevel, Furnace::setMaxFuelLevel, (short) 0)
					// recipe
					.build();
	
	default int getFuelLevel() {
		return hasInventory() ? getInventory().getFuelLevel() : 0;
	}
	
	default void setFuelLevel(int value) {
		getInventory().setFuelLevel(value);
	}
	
	default int getMaxFuelLevel() {
		return hasInventory() ? getInventory().getMaxFuelLevel() : 0;
	}
	
	default void setMaxFuelLevel(int value) {
		getInventory().setMaxFuelLevel(value);
	}
	
	default int getProgress() {
		return hasInventory() ? getInventory().getProgress() : 0;
	}
	
	default void setProgress(int value) {
		getInventory().setProgress(value);
	}
	
	default int getMaxProgress() {
		return hasInventory() ? getInventory().getMaxProgress() : 0;
	}
	
	default void setMaxProgress(int value) {
		getInventory().setMaxProgress(value);
	}
	
	@Override
	default NBTSerializationHandler<? extends Furnace> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
