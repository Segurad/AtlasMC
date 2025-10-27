package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInBundleItemSelected;
import io.netty.buffer.ByteBuf;

public class CorePacketInBundleItemSelected implements PacketIO<PacketInBundleItemSelected> {

	@Override
	public void read(PacketInBundleItemSelected packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.slot = readVarInt(in);
		packet.bundleSlot = readVarInt(in);
	}

	@Override
	public void write(PacketInBundleItemSelected packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.slot, out);
		writeVarInt(packet.bundleSlot, out);
	}

	@Override
	public PacketInBundleItemSelected createPacketData() {
		return new PacketInBundleItemSelected();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInBundleItemSelected.class);
	}

}
