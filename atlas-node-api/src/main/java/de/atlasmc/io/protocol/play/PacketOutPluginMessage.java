package de.atlasmc.io.protocol.play;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import io.netty.buffer.ByteBuf;

@DefaultPacketID(PacketPlay.OUT_PLUGIN_MESSAGE)
public class PacketOutPluginMessage extends AbstractPacket implements PacketPlayOut {
	
	public NamespacedKey channel;
	public ByteBuf data;
	
	@Override
	public int getDefaultID() {
		return OUT_PLUGIN_MESSAGE;
	}

}
