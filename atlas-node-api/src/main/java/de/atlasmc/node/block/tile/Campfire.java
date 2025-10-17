package de.atlasmc.node.block.tile;

import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Campfire extends TileEntity {
	
	static final NBTCodec<Campfire>
	NBT_HANDLER = NBTCodec
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
	default NBTCodec<? extends Campfire> getNBTCodec() {
		return NBT_HANDLER;
	}

}
