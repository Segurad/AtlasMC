package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInPlayerCommand;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerCommand implements PacketIO<PacketInPlayerCommand> {
	
	@Override
	public void read(PacketInPlayerCommand packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setEntityID(readVarInt(in));
		packet.setActionID(readVarInt(in));
		packet.setJumpboost(readVarInt(in));
	}

	@Override
	public void write(PacketInPlayerCommand packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		writeVarInt(packet.getActionID(), out);
		writeVarInt(packet.getJumpboost(), out);
	}

	@Override
	public PacketInPlayerCommand createPacketData() {
		return new PacketInPlayerCommand();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInPlayerCommand.class);
	}
	
}
