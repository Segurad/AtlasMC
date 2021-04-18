package de.atlascore.io.protocol.status;

import java.io.IOException;
import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.status.PacketOutResponse;
import io.netty.buffer.ByteBuf;

public class CorePacketOutResponse extends AbstractPacket implements PacketOutResponse {
	
	private String response;
	
	public CorePacketOutResponse() {
		super(0x00, CoreProtocolAdapter.VERSION);
	}

	public CorePacketOutResponse(String jString) {
		this();
		this.response = jString;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		response = readString(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(response, out);
	}

}
