package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_UPDATE_JIGSAW_BLOCK)
public interface PacketInUpdateJigsawBlock extends PacketPlay, PacketInbound {
	
	public long getPosition();
	public String getName();
	public String getTarget();
	public String getPool();
	public String getFinalState();
	public String getJointtype();
	
	@Override
	default int getDefaultID() {
		return IN_UPDATE_JIGSAW_BLOCK;
	}

}
