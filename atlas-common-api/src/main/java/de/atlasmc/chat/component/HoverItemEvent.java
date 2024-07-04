package de.atlasmc.chat.component;

import de.atlasmc.util.JsonBuffer;

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
