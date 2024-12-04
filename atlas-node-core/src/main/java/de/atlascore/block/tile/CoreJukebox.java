package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.Jukebox;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreJukebox extends CoreTileEntity implements Jukebox {

	protected static final NBTFieldContainer<CoreJukebox> NBT_FIELDS;
	
	protected static final CharKey
	RECORD_ITEM = CharKey.literal("RecordItem");
	
	static {
		NBT_FIELDS = CoreTileEntity.NBT_FIELDS.fork();
		NBT_FIELDS.setField(RECORD_ITEM, (holder, reader) -> {
			Material mat = null;
			if (!NBT_ID.equals(reader.getFieldName())) {
				reader.mark();
				reader.search(NBT_ID);
				mat = Material.getByName(reader.readStringTag());
				reader.reset();
			} else mat = Material.getByName(reader.readStringTag());
			ItemStack item = new ItemStack(mat);
			item.fromNBT(reader);
			holder.setRecordItem(item);
		});
	}
	
	private ItemStack record;
	
	public CoreJukebox(Material type) {
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
	protected NBTFieldContainer<? extends CoreJukebox> getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (!systemData) return;
		if (record == null) return;
		writer.writeCompoundTag(RECORD_ITEM);
		record.toNBT(writer, systemData);
		writer.writeEndTag();
	}

}
