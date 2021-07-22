package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketInbound;

@DefaultPacketID(PacketPlay.IN_ENTITY_ACTION)
public interface PacketInEntityAction extends PacketPlay, PacketInbound {
	
	public int getEntityID();
	public int getActionID();
	public int getJumpBoost();
	
	@Override
	default int getDefaultID() {
		return IN_ENTITY_ACTION;
	}

}
