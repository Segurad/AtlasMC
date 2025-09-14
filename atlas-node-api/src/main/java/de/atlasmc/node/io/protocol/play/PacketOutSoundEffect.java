package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.io.protocol.common.AbstractPacketSoundEffect;

@DefaultPacketID(packetID = PacketPlay.OUT_SOUND_EFFECT, definition = "sound")
public class PacketOutSoundEffect extends AbstractPacketSoundEffect implements PacketPlayOut {
	
	public double x;
	public double y;
	public double z;
	
	@Override
	public int getDefaultID() {
		return OUT_SOUND_EFFECT;
	}

}
