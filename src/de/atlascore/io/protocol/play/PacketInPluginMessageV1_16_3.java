package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.V1_16_3;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInPluginMessage;
import io.netty.buffer.ByteBuf;

public class PacketInPluginMessageV1_16_3 extends AbstractPacket implements PacketInPluginMessage {

	public PacketInPluginMessageV1_16_3() {
		super(0x0B, V1_16_3.version);
	}
	
	private String channel;
	private byte[] data;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		channel = readString(in);
		int len = readVarInt(in);
		data = new byte[len];
		in.readBytes(data);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(channel, out);
		writeVarInt(data.length, out);
		out.writeBytes(data);
	}

	@Override
	public String getChannel() {
		return channel;
	}

	@Override
	public byte[] getData() {
		return data;
	}
	
	

}
