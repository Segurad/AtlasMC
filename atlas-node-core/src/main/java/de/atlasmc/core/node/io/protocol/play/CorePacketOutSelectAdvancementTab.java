package de.atlasmc.core.node.io.protocol.play;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutSelectAdvancementTab;
import io.netty.buffer.ByteBuf;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

public class CorePacketOutSelectAdvancementTab implements PacketIO<PacketOutSelectAdvancementTab> {

	@Override
	public void read(PacketOutSelectAdvancementTab packet, ByteBuf in, ConnectionHandler con) throws IOException {
		if (in.readBoolean())
			packet.tabID = readIdentifier(in);
	}

	@Override
	public void write(PacketOutSelectAdvancementTab packet, ByteBuf out, ConnectionHandler con) throws IOException {
		NamespacedKey tab = packet.tabID;
		if (tab == null) {
			out.writeBoolean(false);
		} else {
			out.writeBoolean(true);
			writeIdentifier(tab, out);
		}
	}

	@Override
	public PacketOutSelectAdvancementTab createPacketData() {
		return new PacketOutSelectAdvancementTab();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSelectAdvancementTab.class);
	}

}
