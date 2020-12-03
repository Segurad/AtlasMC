package de.atlascore.v1_16_3.io.protocol.play;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInChatMessage;

public class PacketInChatMessageV1_16_3 extends AbstractPacket implements PacketInChatMessage {

	public PacketInChatMessageV1_16_3() {
		super(0x03, V1_16_3.version);
	}

	private String msg;
	
	@Override
	public void read(int length, DataInput input) throws IOException {
		msg = readString(input);
	}

	@Override
	public void write(DataOutput output) throws IOException {}

	@Override
	public String getMessage() {
		return msg;
	}

}
