package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_WINDOW_CONFIRMATION)
public interface PacketOutWindowConfirmation extends PacketPlay, PacketOutbound {
	
	public byte getWindowID();
	
	public short getActionNumber();
	
	public boolean isAccepted();
	
	public void setActionNumber(int value);
	
	public void setAccepted(boolean accepted);
	
	public void setWindowID(int id);
	
	@Override
	public default int getDefaultID() {
		return OUT_WINDOW_CONFIRMATION;
	}

}
