package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.AbstractContainerTile;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;

public abstract class CoreAbstractContainerTile<I extends Inventory> extends CoreTileEntity implements AbstractContainerTile<I> {

	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
	protected static final String
	NBT_CUSTOM_NAME = "CustomName",
	NBT_LOCK = "Lock",
	NBT_ITEMS = "Items",
	NBT_LOOT_TABLE = "LootTable",
	NBT_LOOT_TABLE_SEED = "LootTableSeed";
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreTileEntity.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_CUSTOM_NAME, (holder, reader) -> {
			if (holder instanceof AbstractContainerTile)
			((AbstractContainerTile<?>) holder).setCustomName(ChatUtil.toChat(reader.readStringTag()));
			else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_LOCK, (holder, reader) -> {
			if (holder instanceof AbstractContainerTile)
			((AbstractContainerTile<?>) holder).setLock(ChatUtil.toChat(reader.readStringTag()));
			else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_ITEMS, (holder, reader) -> {
			if (holder instanceof AbstractContainerTile) {
				reader.readNextEntry();
				while (reader.getRestPayload() > 0) {
					Inventory inv = ((AbstractContainerTile<?>) holder).getInventory();
					ItemStack item = new ItemStack();
					int slot = item.fromSlot(reader);
					if (slot != -999) inv.setItem(slot, item);
				}
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(NBT_LOOT_TABLE, NBTField.SKIP); // TODO wait for loot table Registry
		NBT_FIELDS.setField(NBT_LOOT_TABLE_SEED, NBTField.SKIP); // ^ how about no?
	}
	
	private Inventory inv;
	private ChatComponent name, lock;
	
	public CoreAbstractContainerTile(Material type, Chunk chunk, int x, int y, int z) {
		super(type, chunk, x, y, z);
	}

	@SuppressWarnings("unchecked")
	@Override
	public I getInventory() {
		if (inv == null) inv = createInventory();
		return (I) inv;
	}
	
	@Override
	public boolean hasInventory() {
		return inv != null;
	}

	@Override
	public void setCustomName(ChatComponent name) {
		this.name = name; 
		if (inv != null) inv.setTitle(name);
	}

	@Override
	public ChatComponent getCustomName() {
		return name;
	}

	@Override
	public void setLock(ChatComponent lock) {
		this.lock = lock;
	}

	@Override
	public boolean hasCustomName() {
		return name != null;
	}

	@Override
	public boolean hasLock() {
		return lock != null;
	}

	@Override
	public ChatComponent getLock() {
		return lock;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (!systemData) return;
		if (hasCustomName())
		writer.writeStringTag(NBT_CUSTOM_NAME, getCustomName().getJsonText());
		if (hasLock())
		writer.writeStringTag(NBT_LOCK, getLock().getJsonText());
		if (inv != null) {
			int count = inv.countItems();
			if (count > 0) {
				writer.writeListTag(NBT_ITEMS, TagType.COMPOUND, count);
				ItemStack[] contents = inv.getContents();
				int slot = -1;
				for (ItemStack item : contents) {
					slot++;
					if (item == null) continue;
					writer.writeCompoundTag(null);
					item.toSlot(writer, systemData, slot);
					writer.writeEndTag();
				}
			} writer.writeListTag(NBT_ITEMS, TagType.TAG_END, 0);
		} writer.writeListTag(NBT_ITEMS, TagType.TAG_END, 0);
	}
	
	@Override
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	protected abstract I createInventory();

}
