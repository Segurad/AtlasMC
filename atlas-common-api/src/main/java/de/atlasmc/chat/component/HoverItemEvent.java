package de.atlasmc.chat.component;

import java.io.IOException;

import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.util.nbt.tag.NBT;

public class HoverItemEvent implements HoverEvent {
	
	public static final CharKey
	JSON_ID = CharKey.literal("id"),
	JSON_COUNT = CharKey.literal("count"),
	JSON_COMPONENT = CharKey.literal("component");
	
	private final String id;
	private final int count;
	private final NBT components;
	
	public HoverItemEvent(String id, int count, NBT components) {
		if (id == null)
			throw new IllegalArgumentException("ID can not be null!");
		this.id = id;
		this.count = count;
		this.components = components;
	}

	@Override
	public HoverAction getAction() {
		return HoverAction.SHOW_ITEM;
	}

	@Override
	public void addContents(NBTWriter writer) throws IOException {
		writer.writeStringTag(JSON_ID, this.id);
		writer.writeIntTag(JSON_COUNT, this.count);
		if (components != null) {
			writer.writeNBT(components);
		}
	}

	public String getId() {
		return id;
	}
	
	public int getCount() {
		return count;
	}
	
	public NBT getComponents() {
		return components;
	}
	
}
