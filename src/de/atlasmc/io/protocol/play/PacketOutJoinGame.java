package de.atlasmc.io.protocol.play;

import de.atlasmc.Gamemode;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_JOIN_GAME)
public interface PacketOutJoinGame extends PacketPlay, PacketOutbound {
	
	public void setOldGamemode(Gamemode gamemode);
	public void setReducedDebugInfo(boolean value);
	public void setEnableRespawnScreen(boolean value);
	public void setDebug(boolean value);
	public void setFlat(boolean flat);
	
	@Override
	public default int getDefaultID() {
		return OUT_JOIN_GAME;
	}

}
