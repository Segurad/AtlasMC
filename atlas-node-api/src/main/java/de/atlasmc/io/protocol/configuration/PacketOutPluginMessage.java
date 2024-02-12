package de.atlasmc.io.protocol.configuration;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketConfiguration.OUT_PLUGIN_MESSAGE)
public class PacketOutPluginMessage extends AbstractPacket implements PacketConfigurationOut {

	public NamespacedKey channel;
	public byte[] data;
	
	@Override
	public int getDefaultID() {
		return OUT_PLUGIN_MESSAGE;
	}
	
}
