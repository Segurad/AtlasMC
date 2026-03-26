package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInChangeContaierSlotState;
import io.netty.buffer.ByteBuf;

public class CorePacketInChangeContainerSlotState implements PacketCodec<PacketInChangeContaierSlotState> {

	@Override
	public void deserialize(PacketInChangeContaierSlotState packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.slot = readVarInt(in);
		packet.windowID = readVarInt(in);
		packet.enabled = in.readBoolean();
	}

	@Override
	public void serialize(PacketInChangeContaierSlotState packet, ByteBuf out, ConnectionHandler con) throws IOException {
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
