package de.atlasmc.io.protocol.play;

import de.atlasmc.chat.ChatMode;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_CLIENT_SETTINGS)
public interface PacketInClientSettings extends PacketPlay, PacketInbound {
	
	public String getLocale();
	public int getViewDistance();
	public ChatMode getChatMode();
	public boolean getChatColor();
	public byte getDisplaySkinParts();
	public int getMainHand();
	
	@Override
	default int getDefaultID() {
		return IN_CLIENT_SETTINGS;
	}

}
