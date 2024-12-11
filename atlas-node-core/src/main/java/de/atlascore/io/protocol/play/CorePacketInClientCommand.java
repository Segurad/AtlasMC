package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInClientCommand;
import de.atlasmc.io.protocol.play.PacketInClientCommand.StatusAction;
import io.netty.buffer.ByteBuf;

public class CorePacketInClientCommand implements PacketIO<PacketInClientCommand> {
	
	@Override
	public void read(PacketInClientCommand packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setAction(StatusAction.getByID(readVarInt(in)));
	}

	@Override
	public void write(PacketInClientCommand packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.getAction().getID(), out);
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
