package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_PLUGIN_MESSAGE)
public class PacketInPluginMessage extends AbstractPacket implements PacketPlayIn {
	
	private String channel;
	private byte[] data;
	
	public String getChannel() {
		return channel;
	}
	
	public void setChannel(String channel) {
		this.channel = channel;
	}
	
	public byte[] getData() {
		return data;
	}
	
	public void setData(byte[] data) {
		this.data = data;
	}
	
	@Override
	public int getDefaultID() {
		return IN_PLUGIN_MESSAGE;
	}

}
