package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutAcknowledgePlayerDigging;
import io.netty.buffer.ByteBuf;

public class CorePacketOutAcknowledgePlayerDigging extends CoreAbstractHandler<PacketOutAcknowledgePlayerDigging> {

	@Override
	public void read(PacketOutAcknowledgePlayerDigging packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setPosition(in.readLong());
		packet.setBlockState(readVarInt(in));
		packet.setStatus(readVarInt(in));
		packet.setSuccessful(in.readBoolean());
	}

	@Override
	public void write(PacketOutAcknowledgePlayerDigging packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeLong(packet.getPosition());
		writeVarInt(packet.getBlockState(), out);
		writeVarInt(packet.getStatus(), out);
		out.writeBoolean(packet.isSuccessful());
	}

	@Override
	public PacketOutAcknowledgePlayerDigging createPacketData() {
		return new PacketOutAcknowledgePlayerDigging();
	}

}
