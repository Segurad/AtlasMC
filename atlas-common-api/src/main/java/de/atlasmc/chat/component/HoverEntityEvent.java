package de.atlasmc.chat.component;

import de.atlasmc.util.JsonBuffer;

public class HoverEntityEvent implements HoverEvent {

	public static final String
	JSON_NAME = "name",
	JSON_TYPE = "type",
	JSON_ID = "id";
	
	private final ChatComponent name;
	private final String type;
	private final String uuid;
	
	public HoverEntityEvent(String type, String uuid, ChatComponent name) {
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
	public void addContents(JsonBuffer buff) {
		if (this.name != null) {
			buff.beginObject(JSON_NAME);
			this.name.addContents(buff);
			buff.endObject();
		}
		buff.append(JSON_TYPE, this.type);
		buff.append(JSON_ID, this.uuid);
	}
	
	public ChatComponent getName() {
		return name;
	}
	
	public String getType() {
		return type;
	}
	
	public String getUUID() {
		return uuid;
	}

}
