package de.atlasmc.io.protocol.play;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import io.netty.buffer.ByteBuf;

@DefaultPacketID(PacketPlay.IN_PLUGIN_MESSAGE)
public class PacketInPluginMessage extends AbstractPacket implements PacketPlayIn {
	
	public NamespacedKey channel;
	public ByteBuf data;
	
	@Override
	public int getDefaultID() {
		return IN_PLUGIN_MESSAGE;
	}

}
