package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_GENERATE_STRUCTURE)
public interface PacketInGenerateStructure extends PacketPlay, PacketInbound {
	
	public long getPosition();
	public int getLevels();
	public boolean getKeepJigsaws();
	
	@Override
	default int getDefaultID() {
		return IN_GENERATE_STRUCTURE;
	}

}
