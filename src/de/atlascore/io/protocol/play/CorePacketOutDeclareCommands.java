package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutDeclareCommands;
import io.netty.buffer.ByteBuf;

public class CorePacketOutDeclareCommands extends AbstractPacket implements PacketOutDeclareCommands {

	// TODO wait for command implementation
	
	public CorePacketOutDeclareCommands() {
		super(CoreProtocolAdapter.VERSION);
	}

	@Override
	public void read(ByteBuf in) throws IOException {
	}

	@Override
	public void write(ByteBuf out) throws IOException {
	}

}
