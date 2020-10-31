package de.atlasmc.io.pack;

import de.atlasmc.io.Packet;

public interface PacketInClientSettings extends Packet {
	
	public String getLocale();
	public int getViewDistance();
	public int getChatMode();
	public boolean getChatColor();
	public byte getDisplaySkinParts();
	public int getMainHand();

}
