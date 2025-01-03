package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.Campfire;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreCampfire extends CoreTileEntity implements Campfire {
	
	protected static final NBTFieldSet<CoreCampfire> NBT_FIELDS;
	
	protected static final CharKey
	ITEMS = CharKey.literal("Items"),
	COOKING_TIMES = CharKey.literal("CookingTimes"),
	COOKING_TOTAL_TIMES = CharKey.literal("CookingTotalTimes");
	
	static {
		NBT_FIELDS = CoreTileEntity.NBT_FIELDS.fork();
		NBT_FIELDS.setField(ITEMS, (holder, reader) -> {
			reader.readNextEntry();
			while (reader.getRestPayload() > 0) {
				Material mat = null;
				if (!NBT_ID.equals(reader.getFieldName())) {
					reader.mark();
					reader.search(NBT_ID);
					mat = Material.getByName(reader.readStringTag());
					reader.reset();
				} else mat = Material.getByName(reader.readStringTag());
				ItemStack item = new ItemStack(mat);
				int slot = item.fromSlot(reader);
				if (slot < 0 ||  slot > 4) continue;
				holder.setItem(slot, item);
			}
		});
		NBT_FIELDS.setField(COOKING_TIMES, (holder, reader) -> {
			holder.setCookingTimes(reader.readIntArrayTag());
		});
		NBT_FIELDS.setField(COOKING_TOTAL_TIMES, (holder, reader) -> {
			holder.setTotalCookingTimes(reader.readIntArrayTag());
		});
	}
	
	private ItemStack[] items;
	private int[] cookingTimes;
	private int[] cookingTotalTimes;

	public CoreCampfire(Material type) {
		super(type);
		cookingTimes = new int[4];
		cookingTotalTimes = new int[4];
		items = new ItemStack[4];
	}

	@Override
	public ItemStack[] getItems() {
		return items;
	}

	@Override
	public ItemStack getItem(int index) {
		return items[index];
	}

	@Override
	public void setItem(int index, ItemStack item) {
		this.items[index] = item;
	}

	@Override
	public int[] getCookingTimes() {
		return cookingTimes;
	}

	@Override
	public void setCookingTime(int index, int time) {
		this.cookingTimes[index] = time;
	}

	@Override
	public int getCookingTime(int index) {
		return cookingTimes[index];
	}

	@Override
	public int[] getTotalCookingTimes() {
		return cookingTotalTimes;
	}

	@Override
	public void setTotalCookingTime(int index, int time) {
		this.cookingTotalTimes[index] = time;
	}

	@Override
	public int getTotalCookingTime(int index) {
		return cookingTotalTimes[index];
	}

	@Override
	public void setItems(ItemStack[] items) {
		for (int i = 0; i < 4 && i < items.length; i++) {
			this.items[i] = items[i];
		}
	}

	@Override
	public void setCookingTimes(int[] cookingTimes) {
		for (int i = 0; i < 4 && i < items.length; i++) {
			this.cookingTimes[i] = cookingTimes[i];
		}
	}

	@Override
	public void setTotalCookingTimes(int[] totalCookingTimes) {
		for (int i = 0; i < 4 && i < items.length; i++) {
			this.cookingTotalTimes[i] = cookingTotalTimes[i];
		}
	}
	
	@Override
	protected NBTFieldSet<? extends CoreCampfire> getFieldSetRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		int count = 0;
		for (int i = 0; i < 4; i++) if (items[i] != null) count++;
		writer.writeListTag(ITEMS, TagType.COMPOUND, count);
		for (int i = 0; i < 4; i++) {
			if (items[i] == null)
				continue;
			writer.writeCompoundTag();	
			items[i].toSlot(writer, systemData, i);
			writer.writeEndTag();
		}
		writer.writeIntArrayTag(COOKING_TIMES, getCookingTimes());
		writer.writeIntArrayTag(COOKING_TOTAL_TIMES, getTotalCookingTimes());
	}

}
