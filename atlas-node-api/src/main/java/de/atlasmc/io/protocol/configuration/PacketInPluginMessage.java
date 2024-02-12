package de.atlasmc.io.protocol.configuration;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import io.netty.buffer.ByteBuf;

@DefaultPacketID(PacketConfiguration.IN_PLUGIN_MESSAGE)
public class PacketInPluginMessage extends AbstractPacket implements PacketConfigurationIn {

	public NamespacedKey channel;
	public ByteBuf data;
	
	@Override
	public int getDefaultID() {
		return IN_PLUGIN_MESSAGE;
	}
	
}
