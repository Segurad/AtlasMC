package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.IN_WINDOW_CONFIRMATION)
public class PacketInWindowConfirmation extends AbstractPacket implements PacketPlayIn {

	private int windowID;
	private int actionNumber;
	private boolean accepted;
	
	public int getWindowID() {
		return windowID;
	}
	
	public void setWindowID(int windowID) {
		this.windowID = windowID;
	}
	
	public int getActionNumber() {
		return actionNumber;
	}
	
	public void setActionNumber(int actionNumber) {
		this.actionNumber = actionNumber;
	}
	
	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	
	public boolean isAccepted() {
		return accepted;
	}
	
	@Override
	public int getDefaultID() {
		return IN_WINDOW_CONFIRMATION;
	}
	
}
