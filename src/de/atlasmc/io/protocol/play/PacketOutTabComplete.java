package de.atlasmc.io.protocol.play;

import de.atlasmc.chat.component.ChatComponent;
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
		private final ChatComponent tooltip;
		
		public Match(String match, ChatComponent tooltip) {
			this.match = match;
			this.tooltip = tooltip;
		}
		
		public String getMatch() {
			return match;
		}
		
		public ChatComponent getToolTip() {
			return tooltip;
		}
		
		public boolean hasToolTip() {
			return tooltip != null;
		}
		
	}

}
