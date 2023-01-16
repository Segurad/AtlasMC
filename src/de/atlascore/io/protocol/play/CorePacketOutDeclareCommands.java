package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutDeclareCommands;
import io.netty.buffer.ByteBuf;

public class CorePacketOutDeclareCommands extends PacketIO<PacketOutDeclareCommands> {

	@Override
	public void read(PacketOutDeclareCommands packet, ByteBuf in, ConnectionHandler con) throws IOException {
		
	}

	@Override
	public void write(PacketOutDeclareCommands packet, ByteBuf out, ConnectionHandler con) throws IOException {
		
	}

	@Override
	public PacketOutDeclareCommands createPacketData() {
		return new PacketOutDeclareCommands();
	}

	// TODO command implementation

}
