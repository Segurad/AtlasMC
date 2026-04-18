package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInClientCommand;
import de.atlasmc.node.io.protocol.play.PacketInClientCommand.StatusAction;
import de.atlasmc.util.enums.EnumUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketInClientCommand implements PacketCodec<PacketInClientCommand> {
	
	@Override
	public void deserialize(PacketInClientCommand packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.action = EnumUtil.getByID(StatusAction.class, readVarInt(in));
	}

	@Override
	public void serialize(PacketInClientCommand packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.action.getID(), out);
	}

	@Override
	public PacketInClientCommand createPacketData() {
		return new PacketInClientCommand();
	}
	
	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInClientCommand.class);
	}

}
