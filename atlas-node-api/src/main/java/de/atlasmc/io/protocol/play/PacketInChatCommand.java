package de.atlasmc.io.protocol.play;

import java.util.BitSet;
import java.util.Map;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_CHAT_COMMAND)
public class PacketInChatCommand extends AbstractPacket implements PacketPlayIn {

	public String command;
	public long commandTimestamp;
	public long salt;
	public Map<String, byte[]> arguments;
	public int messageCount;
	public BitSet acknowledge;
	
	@Override
	public int getDefaultID() {
		return PacketPlay.IN_CHAT_COMMAND;
	}

}
