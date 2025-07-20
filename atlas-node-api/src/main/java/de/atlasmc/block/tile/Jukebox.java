package de.atlasmc.block.tile;

import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Jukebox extends TileEntity {
	
	public static final NBTSerializationHandler<Jukebox>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Jukebox.class)
					.include(TileEntity.NBT_HANDLER)
					.typeComponentField("RecordItem", Jukebox::getRecordItem, Jukebox::setRecordItem, ItemStack.NBT_HANDLER)
					.longField("ticks_since_song_started", Jukebox::getTicksSinceSongStarted, Jukebox::setTicksSinceSongStarted)
					.build();
	
	ItemStack getRecordItem();
	
	void setRecordItem(ItemStack record);

	long getTicksSinceSongStarted();
	
	void setTicksSinceSongStarted(long ticks);
	
	@Override
	default NBTSerializationHandler<? extends Jukebox> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
