package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.readVarInt;
import static de.atlasmc.io.AbstractPacket.writeVarInt;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketInClientStatus;
import de.atlasmc.io.protocol.play.PacketInClientStatus.StatusAction;
import io.netty.buffer.ByteBuf;

public class CorePacketInClientStatus extends CoreAbstractHandler<PacketInClientStatus> {
	
	@Override
	public void read(PacketInClientStatus packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setAction(StatusAction.getByID(readVarInt(in)));
	}

	@Override
	public void write(PacketInClientStatus packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.getAction().getID(), out);
	}

	@Override
	public PacketInClientStatus createPacketData() {
		return new PacketInClientStatus();
	}

}
