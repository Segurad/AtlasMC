package de.atlasmc.io.protocol.play;

import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_TITLE)
public interface PacketOutTitle extends PacketPlay, PacketOutbound {
	
	public TitleAction getAction();
	public ChatComponent getText();
	public int getFadeIn();
	public int getStay();
	public int getFadeOut();
	
	@Override
	public default int getDefaultID() {
		return OUT_TITLE;
	}
	
	public static enum TitleAction {
		SET_TITLE,
		SET_SUBTITLE,
		SET_ACTION_BAR,
		SET_TIME_AND_DISPLAY,
		HIDE,
		RESET;
		
		public static TitleAction getByID(int id) {
			return values()[id];
		}
	}

}
