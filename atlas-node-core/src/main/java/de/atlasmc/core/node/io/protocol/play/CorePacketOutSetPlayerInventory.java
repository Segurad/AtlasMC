package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.io.protocol.play.PacketOutSetPlayerInventory;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetPlayerInventory implements PacketIO<PacketOutSetPlayerInventory> {

	@Override
	public void read(PacketOutSetPlayerInventory packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.slot = readVarInt(in);
		packet.item = ItemStack.STREAM_CODEC.deserialize(in, con.getCodecContext());
	}

	@Override
	public void write(PacketOutSetPlayerInventory packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.slot, out);
		ItemStack.STREAM_CODEC.serialize(packet.item, out, con.getCodecContext());
	}

	@Override
	public PacketOutSetPlayerInventory createPacketData() {
		return new PacketOutSetPlayerInventory();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetPlayerInventory.class);
	}

}
