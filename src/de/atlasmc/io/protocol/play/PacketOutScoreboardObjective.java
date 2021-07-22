package de.atlasmc.io.protocol.play;

import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;
import de.atlasmc.scoreboard.RenderType;

@DefaultPacketID(PacketPlay.OUT_SCOREBOARD_OBJECTIVE)
public interface PacketOutScoreboardObjective extends PacketPlay, PacketOutbound {
	
	public String getName();
	public Mode getMode();
	public ChatComponent getDisplayName();
	public RenderType getRenderType();
	
	@Override
	public default int getDefaultID() {
		return OUT_SCOREBOARD_OBJECTIVE;
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
