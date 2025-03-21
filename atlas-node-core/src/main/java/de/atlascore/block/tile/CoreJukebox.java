package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.tile.Jukebox;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreJukebox extends CoreTileEntity implements Jukebox {

	protected static final NBTFieldSet<CoreJukebox> NBT_FIELDS;
	
	protected static final CharKey
	RECORD_ITEM = CharKey.literal("RecordItem");
	
	static {
		NBT_FIELDS = CoreTileEntity.NBT_FIELDS.fork();
		NBT_FIELDS.setField(RECORD_ITEM, (holder, reader) -> {
			reader.readNextEntry();
			ItemStack item = ItemStack.getFromNBT(reader);
			holder.setRecordItem(item);
		});
	}
	
	private ItemStack record;
	
	public CoreJukebox(BlockType type) {
		super(type);
	}

	@Override
	public ItemStack getRecordItem() {
		return record;
	}

	@Override
	public void setRecordItem(ItemStack record) {
		this.record = record;
	}
	
	@Override
	protected NBTFieldSet<? extends CoreJukebox> getFieldSetRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (!systemData) 
			return;
		if (record == null) 
			return;
		writer.writeCompoundTag(RECORD_ITEM);
		record.toNBT(writer, systemData);
		writer.writeEndTag();
	}

}
