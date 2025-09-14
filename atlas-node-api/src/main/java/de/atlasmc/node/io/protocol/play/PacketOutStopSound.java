package de.atlasmc.node.io.protocol.play;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.SoundCategory;

@DefaultPacketID(packetID = PacketPlay.OUT_STOP_SOUND, definition = "stop_sound")
public class PacketOutStopSound extends AbstractPacket implements PacketPlayOut {
	
	public SoundCategory category;
	public NamespacedKey sound;
	
	@Override
	public int getDefaultID() {
		return OUT_STOP_SOUND;
	}
}
