package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutResourcePackSend;
import io.netty.buffer.ByteBuf;

public class CorePacketOutRessourcePackSend extends AbstractPacket implements PacketOutResourcePackSend {

	private String url, hash;
	
	public CorePacketOutRessourcePackSend() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutRessourcePackSend(String url, String hash) {
		this();
		this.url = url;
		this.hash = hash;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		url = readString(in);
		hash = readString(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(url, out);
		writeString(hash, out);
	}

	@Override
	public String getURL() {
		return url;
	}

	@Override
	public String getHash() {
		return hash;
	}

}
