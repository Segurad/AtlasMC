package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutPluginMessage;
import io.netty.buffer.ByteBuf;

public class CorePacketOutPluginMessage extends AbstractPacket implements PacketOutPluginMessage {

	private String identifier;
	private byte[] data;
	
	public CorePacketOutPluginMessage() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutPluginMessage(String identifier, byte[] data) {
		this();
		this.identifier = identifier;
		this.data = data;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		identifier = readString(in);
		data = new byte[in.readableBytes()];
		in.readBytes(data);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(identifier, out);
		out.writeBytes(data);
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public byte[] getData() {
		return data;
	}

}
