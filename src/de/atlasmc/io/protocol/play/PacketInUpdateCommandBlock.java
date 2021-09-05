package de.atlasmc.io.protocol.play;

import de.atlasmc.block.tile.CommandBlock.Mode;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_UPDATE_COMMAND_BLOCK)
public interface PacketInUpdateCommandBlock extends PacketPlay, PacketInbound {
	
	public long getPosition();
	public String getCommand();
	public Mode getMode();
	public byte getFlags();
	
	@Override
	default int getDefaultID() {
		return IN_UPDATE_COMMAND_BLOCK;
	}

}
