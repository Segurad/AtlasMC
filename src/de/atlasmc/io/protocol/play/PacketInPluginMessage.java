package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_PLUGIN_MESSAGE)
public interface PacketInPluginMessage extends PacketPlay, PacketInbound {
	
	public String getChannel();
	public byte[] getData();
	
	@Override
	default int getDefaultID() {
		return IN_PLUGIN_MESSAGE;
	}

}
