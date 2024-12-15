package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInPlayerCommand;
import de.atlasmc.io.protocol.play.PacketInPlayerCommand.Action;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerCommand implements PacketIO<PacketInPlayerCommand> {
	
	@Override
	public void read(PacketInPlayerCommand packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.entityID = readVarInt(in);
		packet.action = Action.getByID(readVarInt(in));
		packet.jumpboost = readVarInt(in);
	}

	@Override
	public void write(PacketInPlayerCommand packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.entityID, out);
		writeVarInt(packet.action.getID(), out);
		writeVarInt(packet.jumpboost, out);
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
