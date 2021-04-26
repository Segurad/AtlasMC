package de.atlasmc.io.protocol.play;

import de.atlasmc.chat.component.ChatComponent;
import de.atlasmc.io.Packet;

public interface PacketOutPlayerListHeaderAndFooter extends Packet {
	
	public ChatComponent getHeader();
	public ChatComponent getFooter();
	
	@Override
	public default int getDefaultID() {
		return 0x53;
	}

}
