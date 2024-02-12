package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutCommands;
import io.netty.buffer.ByteBuf;

public class CorePacketOutCommands implements PacketIO<PacketOutCommands> {

	@Override
	public void read(PacketOutCommands packet, ByteBuf in, ConnectionHandler con) throws IOException {
		
	}

	@Override
	public void write(PacketOutCommands packet, ByteBuf out, ConnectionHandler con) throws IOException {
		
	}

	@Override
	public PacketOutCommands createPacketData() {
		return new PacketOutCommands();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutCommands.class);
	}

	// TODO command implementation

}
