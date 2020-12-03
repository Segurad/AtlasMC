package de.atlascore.v1_16_3.io.protocol.play;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import de.atlascore.v1_16_3.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInPluginMessage;

public class PacketInPluginMessageV1_16_3 extends AbstractPacket implements PacketInPluginMessage {

	public PacketInPluginMessageV1_16_3() {
		super(0x0B, V1_16_3.version);
	}
	
	private String channel;
	private byte[] data;
	
	@Override
	public void read(int length, DataInput input) throws IOException {
		channel = readString(input);
		int len = readVarInt(input);
		data = new byte[len];
		input.readFully(data);
	}

	@Override
	public void write(DataOutput output) throws IOException {}

	@Override
	public String getChannel() {
		return channel;
	}

	@Override
	public byte[] getData() {
		return data;
	}
	
	

}
