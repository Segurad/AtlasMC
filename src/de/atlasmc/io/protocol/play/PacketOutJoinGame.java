package de.atlasmc.io.protocol.play;

import de.atlasmc.Gamemode;
import de.atlasmc.io.Packet;

public interface PacketOutJoinGame extends Packet {
	
	public void setOldGamemode(Gamemode gamemode);
	public void setReducedDebugInfo(boolean value);
	public void setEnableRespawnScreen(boolean value);
	public void setDebug(boolean value);
	public void setFlat(boolean flat);
	
	@Override
	public default int getDefaultID() {
		return 0x24;
	}

}
