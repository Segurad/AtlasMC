package de.atlasmc.io.protocol.play;

public interface PacketOutWindowConfirmation {
	
	public byte getWindowID();
	public short getActionNumber();
	public boolean isAccepted();

}
