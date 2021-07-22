package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_PLUGIN_MESSAGE)
public interface PacketOutPluginMessage extends PacketPlay, PacketOutbound {
	
	public String getIdentifier();
	public byte[] getData();
	
	@Override
	public default int getDefaultID() {
		return OUT_PLUGIN_MESSAGE;
	}

}
