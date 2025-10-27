package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.inventory.EquipmentSlot;
import de.atlasmc.node.io.protocol.play.PacketInInteract;
import io.netty.buffer.ByteBuf;

public class CorePacketInInteract implements PacketIO<PacketInInteract> {

	@Override
	public void read(PacketInInteract packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.entityID = readVarInt(in);
		packet.type = readVarInt(in);
		packet.x = in.readFloat();
		packet.y = in.readFloat();
		packet.z = in.readFloat();
		packet.hand = readVarInt(in) == 0 ? EquipmentSlot.MAIN_HAND : EquipmentSlot.OFF_HAND;
		packet.sneaking = in.readBoolean();
	}

	@Override
	public void write(PacketInInteract packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.entityID, out);
		writeVarInt(packet.type, out);
		out.writeFloat(packet.x);
		out.writeFloat(packet.y);
		out.writeFloat(packet.z);
		writeVarInt(packet.hand == EquipmentSlot.MAIN_HAND ? 0 : 1, out);
		out.writeBoolean(packet.sneaking);
	}

	@Override
	public PacketInInteract createPacketData() {
		return new PacketInInteract();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInInteract.class);
	}

}
