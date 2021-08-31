package de.atlascore.io.protocol.status;

import java.io.IOException;

import com.google.gson.JsonElement;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.status.PacketOutResponse;
import io.netty.buffer.ByteBuf;

public class CorePacketOutResponse extends AbstractPacket implements PacketOutResponse {
	
	private String response;
	
	public CorePacketOutResponse() {
		super(CoreProtocolAdapter.VERSION);
	}

	public CorePacketOutResponse(JsonElement json) {
		this();
		this.response = json.toString();
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
