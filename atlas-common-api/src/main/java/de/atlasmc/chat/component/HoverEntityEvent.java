package de.atlasmc.chat.component;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.util.nbt.io.NBTWriter;

public class HoverEntityEvent implements HoverEvent {

	public static final String
	JSON_NAME = "name",
	JSON_TYPE = "type",
	JSON_ID = "id";
	
	private final ChatComponent name;
	private final String type;
	private final UUID uuid;
	
	public HoverEntityEvent(String type, UUID uuid, ChatComponent name) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		if (uuid == null)
			throw new IllegalArgumentException("UUID can not be null!");
		this.type = type;
		this.uuid = uuid;
		this.name = name;
	}

	@Override
	public HoverAction getAction() {
		return HoverAction.SHOW_ENTITY;
	}

	@Override
	public void addContents(NBTWriter writer) throws IOException {
		if (this.name != null) {
			writer.writeCompoundTag(JSON_NAME);
			this.name.addContents(writer);
			writer.writeEndTag();
		}
		writer.writeStringTag(JSON_TYPE, this.type);
		writer.writeStringTag(JSON_ID, this.uuid.toString());
	}
	
	public ChatComponent getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public UUID getUUID() {
		return uuid;
	}

}
