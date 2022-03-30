package de.atlasmc.io.protocol.play;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_TAB_COMPLETE)
public interface PacketOutTabComplete extends PacketPlay, PacketOutbound {
	
	@Override
	default int getDefaultID() {
		return OUT_TAB_COMPLETE;
	}
	
	public static class Match {
		
		private final String match;
		private final Chat tooltip;
		
		public Match(String match, Chat tooltip) {
			this.match = match;
			this.tooltip = tooltip;
		}
		
		public String getMatch() {
			return match;
		}
		
		public Chat getToolTip() {
			return tooltip;
		}
		
		public boolean hasToolTip() {
			return tooltip != null;
		}
		
	}

}
