package de.atlasmc.node.block.tile;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.node.inventory.ItemStack;

public interface Campfire extends TileEntity {
	
	static final NBTCodec<Campfire>
	NBT_HANDLER = NBTCodec
					.builder(Campfire.class)
					.include(TileEntity.NBT_HANDLER)
					.codecArraySearchByteIndexField("Items", "slot", Campfire::hasItems, Campfire::getItems, ItemStack.NBT_CODEC)
					.codec("CookingTimes", Campfire::getCookingTimes, Campfire::setCookingTimes, NBTCodecs.INT_ARRAY)
					.codec("CookingTotalTimes", Campfire::getTotalCookingTimes, Campfire::setTotalCookingTimes, NBTCodecs.INT_ARRAY)
					.build();
	
	boolean hasItems();
	
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
