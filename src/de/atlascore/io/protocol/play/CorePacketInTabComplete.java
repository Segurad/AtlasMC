package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketInTabComplete;
import io.netty.buffer.ByteBuf;

public class CorePacketInTabComplete extends CoreAbstractHandler<PacketInTabComplete> {

	@Override
	public void read(PacketInTabComplete packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setTransactionID(readVarInt(in));
		packet.setText(readString(in));
	}

	@Override
	public void write(PacketInTabComplete packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getTransactionID(), out);
		writeString(packet.getText(), out);
	}
	
	@Override
	public PacketInTabComplete createPacketData() {
		return new PacketInTabComplete();
	}

}
