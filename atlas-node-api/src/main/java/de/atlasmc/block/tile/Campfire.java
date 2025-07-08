package de.atlasmc.block.tile;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Campfire extends TileEntity {
	
	static final NBTSerializationHandler<Campfire>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Campfire.class)
					.include(TileEntity.NBT_HANDLER)
					.typeArraySearchByteIndexField("Items", "slot", null, Campfire::getItems, ItemStack.NBT_HANDLER)
					.intArray("CookingTimes", Campfire::getCookingTimes, Campfire::setCookingTimes)
					.intArray("CookingTotalTimes", Campfire::getTotalCookingTimes, Campfire::setTotalCookingTimes)
					.build();
	
	ItemStack[] getItems();
	
	void setItems(ItemStack[] items);
	
	ItemStack getItem(int index);
	
	void setItem(int index, ItemStack item);
	
	int[] getCookingTimes();
	
	void setCookingTimes(int[] cookingTimes);
	
	void setCookingTime(int index, int time);
	
	int getCookingTime(int index);
	
	int[] getTotalCookingTimes();
	
	void setTotalCookingTimes(int[] totalCookingTimes);
	
	void setTotalCookingTime(int index, int time);
	
	int getTotalCookingTime(int index);
	
	@Override
	default NBTSerializationHandler<? extends Campfire> getNBTHandler() {
		return NBT_HANDLER;
	}

}
