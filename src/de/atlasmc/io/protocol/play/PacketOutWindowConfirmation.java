package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_WINDOW_CONFIRMATION)
public class PacketOutWindowConfirmation extends AbstractPacket implements PacketPlayOut {
	
	private int actionNumber;
	private int windowID;
	private boolean accepted;
	
	public int getActionNumber() {
		return actionNumber;
	}

	public int getWindowID() {
		return windowID;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setActionNumber(int actionNumber) {
		this.actionNumber = actionNumber;
	}

	public void setWindowID(int windowID) {
		this.windowID = windowID;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

	@Override
	public int getDefaultID() {
		return OUT_WINDOW_CONFIRMATION;
	}

}
