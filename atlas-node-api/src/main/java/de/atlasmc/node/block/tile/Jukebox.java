package de.atlasmc.node.block.tile;

import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Jukebox extends TileEntity {
	
	public static final NBTCodec<Jukebox>
	NBT_HANDLER = NBTCodec
					.builder(Jukebox.class)
					.include(TileEntity.NBT_HANDLER)
					.typeCompoundField("RecordItem", Jukebox::getRecordItem, Jukebox::setRecordItem, ItemStack.NBT_HANDLER)
					.longField("ticks_since_song_started", Jukebox::getTicksSinceSongStarted, Jukebox::setTicksSinceSongStarted)
					.build();
	
	ItemStack getRecordItem();
	
	void setRecordItem(ItemStack record);

	long getTicksSinceSongStarted();
	
	void setTicksSinceSongStarted(long ticks);
	
	@Override
	default NBTCodec<? extends Jukebox> getNBTCodec() {
		return NBT_HANDLER;
	}
	
}
