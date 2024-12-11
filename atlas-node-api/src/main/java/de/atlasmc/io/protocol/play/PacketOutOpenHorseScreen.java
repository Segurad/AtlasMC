package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_OPEN_HORSE_SCREEN, definition = "horse_screen_open")
public class PacketOutOpenHorseScreen extends AbstractPacket implements PacketPlayOut {
	
	public int windowID;
	public int slots;
	public int entityID;
	
	@Override
	public int getDefaultID() {
		return OUT_OPEN_HORSE_SCREEN;
	}

}
