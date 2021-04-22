package de.atlascore.io.protocol.login;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.login.PacketOutEncryptionRequest;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEncryptionRequest extends AbstractPacket implements PacketOutEncryptionRequest {

	public CorePacketOutEncryptionRequest() {
		super(CoreProtocolAdapter.VERSION);
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
