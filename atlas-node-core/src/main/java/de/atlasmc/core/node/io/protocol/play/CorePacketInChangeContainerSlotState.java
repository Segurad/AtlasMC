package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketInChangeContaierSlotState;
import io.netty.buffer.ByteBuf;

public class CorePacketInChangeContainerSlotState implements PacketIO<PacketInChangeContaierSlotState> {

	@Override
	public void read(PacketInChangeContaierSlotState packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.slot = readVarInt(in);
		packet.windowID = readVarInt(in);
		packet.enabled = in.readBoolean();
	}

	@Override
	public void write(PacketInChangeContaierSlotState packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.slot, out);
		writeVarInt(packet.windowID, out);
		out.writeBoolean(packet.enabled);
	}

	@Override
	public PacketInChangeContaierSlotState createPacketData() {
		return new PacketInChangeContaierSlotState();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInChangeContaierSlotState.class);
	}

}
