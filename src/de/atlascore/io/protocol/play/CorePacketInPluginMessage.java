package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInPluginMessage;
import io.netty.buffer.ByteBuf;

public class CorePacketInPluginMessage extends AbstractPacket implements PacketInPluginMessage {

	public CorePacketInPluginMessage() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	private String channel;
	private byte[] data;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		channel = readString(in);
		data = new byte[in.readableBytes()];
		in.readBytes(data);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(channel, out);
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
