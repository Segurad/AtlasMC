package de.atlasmc.io.protocol.play;

import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.io.Packet;

public interface PacketOutTabComplete extends Packet {
	
	@Override
	default int getDefaultID() {
		return 0x0F;
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
