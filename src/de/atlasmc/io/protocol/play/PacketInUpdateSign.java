package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_UPDATE_SIGN)
public interface PacketInUpdateSign extends PacketPlay, PacketInbound {
	
	public long getPosition();
	public String getLine1();
	public String getLine2();
	public String getLine3();
	public String getLine4();
	
	@Override
	public default int getDefaultID() {
		return IN_UPDATE_SIGN;
	}

}
