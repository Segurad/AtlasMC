package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.Allay;
import de.atlasmc.entity.EntityType;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreAllay extends CoreMob implements Allay {

	protected static final NBTFieldSet<CoreAllay> NBT_FIELDS;
	
	private static final CharKey
	NBT_CAN_DUPLICATE = CharKey.literal("CanDuplicate"),
	NBT_DUPLICATION_COOLDOWN = CharKey.literal("DuplicationCooldown"),
	NBT_INVENTORY = CharKey.literal("Inventory");
	
	static {
		NBT_FIELDS = CoreMob.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_CAN_DUPLICATE, (holder, reader) -> {
			holder.setCanDuplicate(reader.readByteTag() == 1);
		});
		NBT_FIELDS.setField(NBT_DUPLICATION_COOLDOWN, (holder, reader) -> {
			holder.setDuplicationCooldown(reader.readLongTag());
		});
		NBT_FIELDS.setField(NBT_INVENTORY, (holder, reader) -> {
			Inventory inv = holder.getInventory();
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				reader.readNextEntry();
				ItemStack item = ItemStack.getFromNBT(reader);
				item.fromNBT(reader);
				inv.addItem(item);
			}
			reader.readNextEntry();
		});
	}
	
	private Inventory inv;
	private boolean canDuplicate;
	private long duplicationCooldown;
	
	public CoreAllay(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected NBTFieldSet<? extends CoreAllay> getFieldSetRoot() {
		return NBT_FIELDS;
	}

	@Override
	public Inventory getInventory() {
		if (inv == null)
			inv = InventoryType.GENERIC_9X1.create(this);
		return inv;
	}

	@Override
	public boolean canDuplicate() {
		return canDuplicate;
	}

	@Override
	public void setCanDuplicate(boolean canDuplicate) {
		this.canDuplicate = canDuplicate;
	}

	@Override
	public long getDuplicationCooldown() {
		return duplicationCooldown;
	}

	@Override
	public void setDuplicationCooldown(long cooldown) {
		this.duplicationCooldown = cooldown;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (!systemData)
			return;
		writer.writeByteTag(NBT_CAN_DUPLICATE, canDuplicate);
		writer.writeLongTag(NBT_DUPLICATION_COOLDOWN, duplicationCooldown);
		int itemCount = inv != null ? inv.countItems() : 0;
		if (itemCount > 0) {
			writer.writeListTag(NBT_INVENTORY, TagType.COMPOUND, itemCount);
			for (ItemStack item : inv) {
				item.toNBT(writer, systemData);
				writer.writeEndTag();
			}
		}
	}

}
