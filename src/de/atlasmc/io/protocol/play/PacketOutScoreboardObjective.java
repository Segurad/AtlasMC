package de.atlasmc.io.protocol.play;

import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.io.Packet;
import de.atlasmc.scoreboard.RenderType;

public interface PacketOutScoreboardObjective extends Packet {
	
	public String getName();
	public Mode getMode();
	public ChatComponent getDisplayName();
	public RenderType getRenderType();
	
	@Override
	public default int getDefaultID() {
		return 0x4A;
	}
	
	public static enum Mode {
		CREATE,
		REMOVE,
		UPDATE_DISPLAY;
		
		public static Mode getByID(int id) {
			return values()[id];
		}
	}

}
