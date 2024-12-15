package de.atlasmc.io.protocol.common;

import java.util.List;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.util.EnumID;
import de.atlasmc.util.EnumValueCache;

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
	
	public static enum Label implements EnumID, EnumValueCache {
		
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
		
		private static List<Label> VALUES;
		
		public int getID() {
			return ordinal();
		}
		
		public static Label getByID(int id) {
			return getValues().get(id);
		}
		
		/**
		 * Returns a immutable List of all Types.<br>
		 * This method avoid allocation of a new array not like {@link #values()}.
		 * @return list
		 */
		public static List<Label> getValues() {
			if (VALUES == null)
				VALUES = List.of(values());
			return VALUES;
		}
		
		/**
		 * Releases the system resources used from the values cache
		 */
		public static void freeValues() {
			VALUES = null;
		}
		
	}
	
}
