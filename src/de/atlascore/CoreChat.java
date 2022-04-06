package de.atlascore;

import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;

public class CoreChat implements Chat {
	
	private String json;
	private String legacy;
	
	public CoreChat(String json, String legacy) {
		if (json == null && legacy == null)
			throw new IllegalArgumentException("Only json or legacy may be null not both!");
		this.json = json;
		this.legacy = legacy;
	}
	
	@Override
	public String getLegacyText() {
		if (legacy == null) {
			synchronized (this) {
				if (legacy == null)
					legacy = ChatUtil.legacyFromJson(json);
			}
		}
		return legacy;
	}
	@Override
	public String getJsonText() {
		if (json == null) {
			synchronized (this) {
				if (json == null)
					json = ChatUtil.jsonFromLegacy(json);
			}
		}
		return json;
	}
	@Override
	public String getText() {
		return json != null ? json : legacy;
	}

}
