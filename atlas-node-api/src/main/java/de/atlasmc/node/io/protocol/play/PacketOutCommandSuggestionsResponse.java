package de.atlasmc.node.io.protocol.play;

import java.util.List;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_COMMAND_SUGGESTIONS_RESPONSE, definition = "command_suggestions")
public class PacketOutCommandSuggestionsResponse extends AbstractPacket implements PacketPlayOut {
	
	public int transactionID;
	public int start;
	public int length;
	public List<Match> matches;

	@Override
	public int getDefaultID() {
		return OUT_COMMAND_SUGGESTIONS_RESPONSE;
	}
	
	public static class Match {
		
		private final String match;
		private final String tooltip;
		
		public Match(String match, String tooltip) {
			this.match = match;
			this.tooltip = tooltip;
		}
		
		public String getMatch() {
			return match;
		}
		
		public String getToolTip() {
			return tooltip;
		}
		
		public boolean hasToolTip() {
			return tooltip != null;
		}
		
	}

}
