package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_UPDATE_COMMAND_BLOCK_MINECART)
public interface PacketInUpdateCommandBlockMinecart extends PacketPlay, PacketInbound {
	
	public int getEntityID();
	public String getCommand();
	public boolean getTrackOutput();
	
	@Override
	default int getDefaultID() {
		return IN_UPDATE_COMMAND_BLOCK_MINECART;
	}

}
