package de.atlasmc.node.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = PacketPlay.OUT_OPEN_SIGN_EDITOR, definition = "open_sign_editor")
public class PacketOutOpenSignEditor extends AbstractPacket implements PacketPlayOut {
	
	public long position;
	public boolean frontText;
	
	@Override
	public int getDefaultID() {
		return OUT_OPEN_SIGN_EDITOR;
	}

}
