package de.atlasmc.io.handshake;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(packetID = 0x00)
public class PacketMinecraftHandshake extends AbstractPacket implements PacketHandshake {

	private int protocolVersion;
	private String address;
	private int port;
	private int nextState;
	
	public int getProtocolVersion() {
		return protocolVersion;
	}
	
	public int getPort() {
		return port;
	}
	
	public String getAddress() {
		return address;
	}
	
	public int getNextState() {
		return nextState;
	}
	
	public void setProtocolVersion(int protocolVersion) {
		this.protocolVersion = protocolVersion;
	}
	
	public void setPort(int port) {
		this.port = port;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public void setNextState(int nextState) {
		this.nextState = nextState;
	}
	
	@Override
	public int getDefaultID() {
		return 0x00;
	}

}
