package de.atlasmc.chat.component;

import java.io.IOException;
import java.io.StringWriter;

import de.atlasmc.chat.ChatFormatException;
import de.atlasmc.inventory.ItemStack;
import de.atlasmc.util.JsonBuffer;
import de.atlasmc.util.nbt.io.SNBTWriter;

public class HoverItemEvent implements HoverEvent {
	
	public static final String 
	JSON_ID = "id",
	JSON_COUNT = "count",
	JSON_TAG = "tag";
	
	private final String id;
	private final int count;
	private final String tag;
	
	public HoverItemEvent(String id, int count, String tag) {
		if (id == null)
			throw new IllegalArgumentException("ID can not be null!");
		this.id = id;
		this.count = count;
		this.tag = tag;
	}
	
	public HoverItemEvent(ItemStack item) {
		this(item, false);
	}
	
	public HoverItemEvent(ItemStack item, boolean systemData) {
		if (item == null)
			throw new IllegalArgumentException("Item can not be null!");
		this.id = item.getType().getNamespacedKeyRaw();
		this.count = item.getAmount();
		if (item.hasItemMeta()) {
			StringWriter writer = new StringWriter();
			SNBTWriter nbtWriter = new SNBTWriter(writer);
			try {
				item.getItemMeta().toNBT(nbtWriter, systemData);
			} catch (IOException e) {
				throw new ChatFormatException("Error while writing item meta!", e);
			}
			this.tag = writer.toString();
		} else {
			this.tag = null;
		}
	}

	@Override
	public HoverAction getAction() {
		return HoverAction.SHOW_ITEM;
	}

	@Override
	public void addContents(JsonBuffer buff) {
		buff.append(JSON_ID, this.id);
		buff.append(JSON_COUNT, this.count);
		if (tag != null) {
			buff.appendText(JSON_TAG, this.tag);
		}
	}

	public String getId() {
		return id;
	}
	
	public int getCount() {
		return count;
	}
	
	public String getTag() {
		return tag;
	}
	
}
