package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.AbstractContainerTile;
import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTField;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public abstract class CoreAbstractContainerTile<I extends Inventory> extends CoreTileEntity implements AbstractContainerTile<I> {

	protected static final NBTFieldSet<CoreAbstractContainerTile<?>> NBT_FIELDS;
	
	protected static final CharKey
	NBT_LOCK = CharKey.literal("Lock"),
	NBT_ITEMS = CharKey.literal("Items"),
	NBT_LOOT_TABLE = CharKey.literal("LootTable"),
	NBT_LOOT_TABLE_SEED = CharKey.literal("LootTableSeed"),
	NBT_CUSTOM_NAME = CharKey.literal("CustomName");
	
	static {
		NBT_FIELDS = CoreTileEntity.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_LOCK, (holder, reader) -> {
			holder.setLock(ChatUtil.toChat(reader.readStringTag()));
		});
		NBT_FIELDS.setField(NBT_ITEMS, (holder, reader) -> {
			reader.readNextEntry();
			Inventory inv = ((AbstractContainerTile<?>) holder).getInventory();
			while (reader.getRestPayload() > 0) {
				ItemStack item = ItemStack.getFromNBT(reader, false);
				if (item == null)
					continue;
				int slot = item.fromSlot(reader);
				if (slot != -999) 
					inv.setItem(slot, item);
			}
		});
		NBT_FIELDS.setField(NBT_LOOT_TABLE, NBTField.skip()); // TODO wait for loot table Registry
		NBT_FIELDS.setField(NBT_LOOT_TABLE_SEED, NBTField.skip()); // ^ how about no?
		NBT_FIELDS.setField(NBT_CUSTOM_NAME, (holder, reader) -> {
			holder.setCustomName(ChatUtil.toChat(reader.readStringTag()));
		});
	}
	
	private Inventory inv;
	private Chat name;
	private Chat lock;
	
	public CoreAbstractContainerTile(Material type) {
		super(type);
	}

	@SuppressWarnings("unchecked")
	@Override
	public I getInventory() {
		if (inv == null) {
			inv = createInventory();
			inv.setTitle(name);
		}
		return (I) inv;
	}
	
	@Override
	public boolean hasInventory() {
		return inv != null;
	}

	@Override
	public void setCustomName(Chat name) {
		this.name = name; 
		if (inv != null) 
			inv.setTitle(name);
	}

	@Override
	public Chat getCustomName() {
		return name;
	}

	@Override
	public void setLock(Chat lock) {
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
	public Chat getLock() {
		return lock;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (!systemData) 
			return;
		if (hasCustomName())
			writer.writeStringTag(NBT_CUSTOM_NAME, getCustomName().toJsonText());
		if (hasLock())
			writer.writeStringTag(NBT_LOCK, getLock().toJsonText());
		if (inv != null) {
			int count = inv.countItems();
			if (count > 0) {
				writer.writeListTag(NBT_ITEMS, TagType.COMPOUND, count);
				ItemStack[] contents = inv.getContents();
				int slot = -1;
				for (ItemStack item : contents) {
					slot++;
					if (item == null) 
						continue;
					writer.writeCompoundTag();
					item.toSlot(writer, systemData, slot);
					writer.writeEndTag();
				}
			}
		}
		if (name != null) {
			writer.writeStringTag(NBT_CUSTOM_NAME, name.toJsonText());
		}
	}
	
	@Override
	protected NBTFieldSet<? extends CoreAbstractContainerTile<?>> getFieldSetRoot() {
		return NBT_FIELDS;
	}
	
	protected abstract I createInventory();

}
