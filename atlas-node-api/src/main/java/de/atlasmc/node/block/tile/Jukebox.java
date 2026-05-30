package de.atlasmc.node.block.tile;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.util.annotation.NotNull;

public interface Jukebox extends TileEntity {
	
	@NotNull
	public static final NBTCodec<Jukebox>
	NBT_CODEC = NBTCodec
					.builder(Jukebox.class)
					.include(TileEntity.NBT_CODEC)
					.codec("RecordItem", Jukebox::getRecordItem, Jukebox::setRecordItem, ItemStack.NBT_CODEC)
					.longField("ticks_since_song_started", Jukebox::getTicksSinceSongStarted, Jukebox::setTicksSinceSongStarted)
					.build();
	
	ItemStack getRecordItem();
	
	void setRecordItem(ItemStack record);

	long getTicksSinceSongStarted();
	
	void setTicksSinceSongStarted(long ticks);
	
	@Override
	default NBTCodec<? extends Jukebox> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
