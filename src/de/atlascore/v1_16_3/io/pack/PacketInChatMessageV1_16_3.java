package de.atlascore.v1_16_3.io.pack;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.pack.PacketInChatMessage;

public class PacketInChatMessageV1_16_3 extends AbstractPacket implements PacketInChatMessage {

	public PacketInChatMessageV1_16_3() {
		super(0x03, V1_16_3.version);
	}

	private String msg;
	
	@Override
	public void read(int length, DataInputStream input) throws IOException {
		msg = readString(input);
	}

	@Override
	public void write(DataOutputStream output) throws IOException {}

	@Override
	public String getMessage() {
		return msg;
	}

}
