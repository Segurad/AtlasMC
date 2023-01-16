package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInEntityAction;
import io.netty.buffer.ByteBuf;

public class CorePacketInEntityAction extends PacketIO<PacketInEntityAction> {
	
	@Override
	public void read(PacketInEntityAction packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setEntityID(readVarInt(in));
		packet.setActionID(readVarInt(in));
		packet.setJumpboost(readVarInt(in));
	}

	@Override
	public void write(PacketInEntityAction packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		writeVarInt(packet.getActionID(), out);
		writeVarInt(packet.getJumpboost(), out);
	}

	@Override
	public PacketInEntityAction createPacketData() {
		return new PacketInEntityAction();
	}
	
}
