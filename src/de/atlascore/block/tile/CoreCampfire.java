package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.Campfire;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.TagType;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.Chunk;

public class CoreCampfire extends CoreTileEntity implements Campfire {
	
	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
	protected static final String
	ITEMS = "Items",
	COOKING_TIMES = "CookingTimes",
	COOKING_TOTAL_TIMES = "CookingTotalTimes";
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreTileEntity.NBT_FIELDS);
		NBT_FIELDS.setField(ITEMS, (holder, reader) -> {
			if (holder instanceof Campfire) {
				reader.readNextEntry();
				while (reader.getRestPayload() > 0) {
					ItemStack item = new ItemStack();
					int slot = item.fromSlot(reader);
					if (slot < 0 ||  slot > 4) continue;
					((Campfire) holder).setItem(slot, item);
				}
			} else reader.skipTag();
		});
		NBT_FIELDS.setField(COOKING_TIMES, (holder, reader) -> {
			if (holder instanceof Campfire)
			((Campfire) holder).setCookingTimes(reader.readIntArrayTag());
			else reader.skipTag();
		});
		NBT_FIELDS.setField(COOKING_TOTAL_TIMES, (holder, reader) -> {
			if (holder instanceof Campfire)
				((Campfire) holder).setTotalCookingTimes(reader.readIntArrayTag());
				else reader.skipTag();
		});
	}
	
	private ItemStack[] items;
	private int[] cookingTimes, cookingTotalTimes;

	public CoreCampfire(Material type, Chunk chunk, int x, int y, int z) {
		super(type, chunk, x, y, z);
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
	protected NBTFieldContainer getFieldContainerRoot() {
		return NBT_FIELDS;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		int count = 0;
		for (int i = 0; i < 4; i++) if (items[i] != null) count++;
		writer.writeListTag(ITEMS, TagType.COMPOUND, count);
		for (int i = 0; i < 4; i++) {
			if (items[i] != null) items[i].toSlot(writer, systemData, i);
		}
		writer.writeIntArrayTag(COOKING_TIMES, getCookingTimes());
		writer.writeIntArrayTag(COOKING_TOTAL_TIMES, getTotalCookingTimes());
	}

}
