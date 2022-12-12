package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketInCloseWindow;
import io.netty.buffer.ByteBuf;

public class CorePacketInCloseWindow extends CoreAbstractHandler<PacketInCloseWindow> {

	@Override
	public void read(PacketInCloseWindow packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setWindowID(in.readByte());
	}

	@Override
	public void write(PacketInCloseWindow packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeByte(packet.getWindowID());
	}

	@Override
	public PacketInCloseWindow createPacketData() {
		return new PacketInCloseWindow();
	}

}
