package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_UPDATE_COMMAND_BLOCK)
public interface PacketInUpdateCommandBlock extends PacketPlay, PacketInbound {
	
	public long getPosition();
	public String getCommand();
	public int getMode();
	public byte Flags();
	
	@Override
	default int getDefaultID() {
		return IN_UPDATE_COMMAND_BLOCK;
	}

}
