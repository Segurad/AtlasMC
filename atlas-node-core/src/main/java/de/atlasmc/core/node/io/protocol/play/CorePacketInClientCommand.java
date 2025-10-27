package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInClientCommand;
import de.atlasmc.node.io.protocol.play.PacketInClientCommand.StatusAction;
import io.netty.buffer.ByteBuf;

public class CorePacketInClientCommand implements PacketIO<PacketInClientCommand> {
	
	@Override
	public void read(PacketInClientCommand packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.action = StatusAction.getByID(readVarInt(in));
	}

	@Override
	public void write(PacketInClientCommand packet, ByteBuf out, ConnectionHandler con) throws IOException {
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
