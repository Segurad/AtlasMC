package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.inventory.ItemStack;
import de.atlasmc.node.io.protocol.play.PacketOutSetContainerSlot;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetContainerSlot implements PacketIO<PacketOutSetContainerSlot> {

	@Override
	public void read(PacketOutSetContainerSlot packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.windowID = in.readByte();
		packet.stateID = readVarInt(in);
		packet.slot = in.readShort();
		packet.item = ItemStack.STREAM_CODEC.deserialize(in, handler.getCodecContext());
	}

	@Override
	public void write(PacketOutSetContainerSlot packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.windowID);
		writeVarInt(packet.stateID, out);
		out.writeShort(packet.slot);
		ItemStack.STREAM_CODEC.serialize(packet.item, out, handler.getCodecContext());
	}

	@Override
	public PacketOutSetContainerSlot createPacketData() {
		return new PacketOutSetContainerSlot();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetContainerSlot.class);
	}

}
