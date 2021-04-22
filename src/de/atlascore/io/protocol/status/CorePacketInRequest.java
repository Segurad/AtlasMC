package de.atlascore.io.protocol.status;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.status.PacketInRequest;
import io.netty.buffer.ByteBuf;

public class CorePacketInRequest extends AbstractPacket implements PacketInRequest {

	public CorePacketInRequest() {
		super(CoreProtocolAdapter.VERSION);
	}

	@Override
	public void read(ByteBuf in) throws IOException {}

	@Override
	public void write(ByteBuf out) throws IOException {}

}
