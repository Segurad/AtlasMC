package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_WINDOW_CONFIRMATION)
public interface PacketInWindowConfirmation extends PacketPlay, PacketInbound {

	public byte getWindowID();
	public short getActionNumber();
	public boolean getAccepted();
	
	@Override
	default int getDefaultID() {
		return IN_WINDOW_CONFIRMATION;
	}
	
}
