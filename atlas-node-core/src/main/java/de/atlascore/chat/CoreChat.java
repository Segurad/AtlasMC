package de.atlascore.chat;

import de.atlasmc.chat.Chat;
import de.atlasmc.chat.ChatUtil;
import de.atlasmc.chat.component.ChatComponent;

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
	public String toLegacyText() {
		if (legacy == null) {
			synchronized (this) {
				if (legacy == null)
					legacy = ChatUtil.jsonToLegacy(json);
			}
		}
		return legacy;
	}
	
	@Override
	public String toJsonText() {
		if (json == null) {
			synchronized (this) {
				if (json == null)
					json = ChatUtil.legacyToJson(json);
			}
		}
		return json;
	}
	
	@Override
	public String toText() {
		return json != null ? json : legacy;
	}
	
	@Override
	public String toString() {
		return toText();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((json == null) ? 0 : json.hashCode());
		result = prime * result + ((legacy == null) ? 0 : legacy.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CoreChat other = (CoreChat) obj;
		if (json == null) {
			if (other.json != null)
				return false;
		} else if (!json.equals(other.json))
			return false;
		if (legacy == null) {
			if (other.legacy != null)
				return false;
		} else if (!legacy.equals(other.legacy))
			return false;
		return true;
	}

	@Override
	public boolean hasLegacy() {
		return legacy != null;
	}

	@Override
	public boolean hasJson() {
		return json != null;
	}
	
	@Override
	public String toRawText() {
		if (json != null)
			return ChatUtil.jsonToRawText(json);
		if (legacy != null)
			return ChatUtil.legacyToRawText(legacy);
		return "";
	}

	@Override
	public ChatComponent toComponent() {
		return ChatUtil.toComponent(this);
	}

}
