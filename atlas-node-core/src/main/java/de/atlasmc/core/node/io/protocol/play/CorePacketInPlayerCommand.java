package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInPlayerCommand;
import de.atlasmc.node.io.protocol.play.PacketInPlayerCommand.Action;
import de.atlasmc.util.enums.EnumUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerCommand implements PacketCodec<PacketInPlayerCommand> {
	
	@Override
	public void deserialize(PacketInPlayerCommand packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.entityID = readVarInt(in);
		packet.action = EnumUtil.getByID(Action.class, readVarInt(in));
		packet.jumpboost = readVarInt(in);
	}

	@Override
	public void serialize(PacketInPlayerCommand packet, ByteBuf out, ConnectionHandler con) throws IOException {
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
