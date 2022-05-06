package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.Jukebox;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;

public class CoreJukebox extends CoreTileEntity implements Jukebox {

	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
	protected static final CharKey
	RECORD_ITEM = CharKey.of("RecordItem");
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreTileEntity.NBT_FIELDS);
		NBT_FIELDS.setField(RECORD_ITEM, (holder, reader) -> {
			if (!(holder instanceof Jukebox)) {
				reader.skipTag();
				return;
			}
			Material mat = null;
			if (!ID.equals(reader.getFieldName())) {
				reader.mark();
				reader.search(ID);
				mat = Material.getByName(reader.readStringTag());
				reader.reset();
			} else mat = Material.getByName(reader.readStringTag());
			ItemStack item = new ItemStack(mat);
			item.fromNBT(reader);
			((Jukebox) holder).setRecordItem(item);
		});
	}
	
	private ItemStack record;
	
	public CoreJukebox(Material type, Chunk chunk, int x, int y, int z) {
		super(type, chunk, x, y, z);
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
	protected NBTFieldContainer getFieldContainerRoot() {
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
