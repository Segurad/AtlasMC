package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_OPEN_SIGN_EDITOR)
public class PacketOutOpenSignEditor extends AbstractPacket implements PacketPlayOut {
	
	private long position;
	private boolean frontText;
	
	public long getPosition() {
		return position;
	}
	
	public void setPosition(long position) {
		this.position = position;
	}
	
	public boolean isFrontText() {
		return frontText;
	}
	
	public void setFrontText(boolean frontText) {
		this.frontText = frontText;
	}
	
	@Override
	public int getDefaultID() {
		return OUT_OPEN_SIGN_EDITOR;
	}

}
