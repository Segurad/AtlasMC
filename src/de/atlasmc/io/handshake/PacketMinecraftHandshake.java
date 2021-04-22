package de.atlasmc.io.handshake;

import java.io.IOException;

import de.atlasmc.io.AbstractPacket;
import io.netty.buffer.ByteBuf;

public class PacketMinecraftHandshake extends AbstractPacket {

	private int protocolVersion;
	private String address;
	private int port;
	private int nextState;
	
	public PacketMinecraftHandshake() {
		super(0x00, 0);
	}
	
	public PacketMinecraftHandshake(int protocolVersion, String address, int port, int nextState) {
		super(0x00, 0);
		this.protocolVersion = protocolVersion;
		this.address = address;
		this.port = port;
		this.nextState = nextState;
	}
	
	public int getProtocolVersion() {
		return protocolVersion;
	}
	
	public int getPort() {
		return port;
	}
	
	public int getNextState() {
		return nextState;
	}
	
	public String getAddress() {
		return address;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		protocolVersion = readVarInt(in);
		address = readString(in);
		port = in.readShort();
		nextState = readVarInt(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(protocolVersion, out);
		writeString(address, out);
		out.writeShort(port);
		writeVarInt(nextState, out);
	}

	@Override
	public int getDefaultID() {
		return 0;
	}

}
