package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_BLOCK_ACTION)
public interface PacketOutBlockAction extends PacketPlay, PacketOutbound {
	
	public long getPosition();
	public int getActionID();
	public int getActionParam();
	public int getBlockType();
	
	@Override
	default int getDefaultID() {
		return OUT_BLOCK_ACTION;
	}

}
