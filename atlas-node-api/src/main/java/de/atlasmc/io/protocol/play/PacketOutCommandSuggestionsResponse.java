package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_COMMAND_SUGGESTIONS_RESPONSE)
public class PacketOutCommandSuggestionsResponse extends AbstractPacket implements PacketPlayOut {
	
	private int transactionID, start, length;
	private List<Match> matches;
	
	public int getTransactionID() {
		return transactionID;
	}

	public int getStart() {
		return start;
	}

	public int getLength() {
		return length;
	}

	public List<Match> getMatches() {
		return matches;
	}

	public void setTransactionID(int transactionID) {
		this.transactionID = transactionID;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public void setMatches(List<Match> matches) {
		this.matches = matches;
	}

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
