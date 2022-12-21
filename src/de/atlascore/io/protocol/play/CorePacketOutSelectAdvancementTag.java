package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutSelectAdvancementTab;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSelectAdvancementTag extends CoreAbstractHandler<PacketOutSelectAdvancementTab> {

	@Override
	public void read(PacketOutSelectAdvancementTab packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		if (in.readBoolean())
			packet.setTabID(readString(in));
	}

	@Override
	public void write(PacketOutSelectAdvancementTab packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		if (packet.getTabID() != null) {
			out.writeBoolean(true);
			writeString(packet.getTabID(), out);
		} else
			out.writeBoolean(false);
	}

	@Override
	public PacketOutSelectAdvancementTab createPacketData() {
		return new PacketOutSelectAdvancementTab();
	}

}
