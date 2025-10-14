package de.atlasmc.node.io.protocol.common;

import java.util.List;

import de.atlasmc.IDHolder;
import de.atlasmc.chat.Chat;
import de.atlasmc.io.AbstractPacket;

public abstract class AbstractPacketServerLinks extends AbstractPacket {

	public List<ServerLink> links;
	
	public static class ServerLink {
		
		public Chat customLabel;
		public Label label;
		public String url;
		
		public ServerLink() {}
		
		public ServerLink(Label label, Chat customLabel, String url) {
			this.label = label;
			this.customLabel = customLabel;
			this.url = url;
		}
		
	}
	
	public static enum Label implements IDHolder {
		
		BUG_REPORT,
		COMMUNITY_GUIDELINES,
		SUPPORT,
		STATUS,
		FEEDBACK,
		COMMUNITY,
		WEBSITE,
		FORUM,
		NEWS,
		ANNOUCEMENTS;
		
		@Override
		public int getID() {
			return ordinal();
		}
		
	}
	
}
