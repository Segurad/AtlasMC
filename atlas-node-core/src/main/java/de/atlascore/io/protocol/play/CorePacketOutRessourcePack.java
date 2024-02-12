package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutResourcePack;
import io.netty.buffer.ByteBuf;

public class CorePacketOutRessourcePack implements PacketIO<PacketOutResourcePack> {

	@Override
	public void read(PacketOutResourcePack packet, ByteBuf in, ConnectionHandler hander) throws IOException {
		packet.setURL(readString(in));
		packet.setHash(readString(in));
		packet.setForced(in.readBoolean());
		if (in.readBoolean()) {
			packet.setPromt(readString(in));
		}
	}

	@Override
	public void write(PacketOutResourcePack packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeString(packet.getURL(), out);
		writeString(packet.getHash(), out);
		out.writeBoolean(packet.isForced());
		String promt = packet.getPromt();
		if (promt == null) {
			out.writeBoolean(false);
		} else {
			out.writeBoolean(true);
			writeString(promt, out);
		}
	}

	@Override
	public PacketOutResourcePack createPacketData() {
		return new PacketOutResourcePack();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutResourcePack.class);
	}

}
