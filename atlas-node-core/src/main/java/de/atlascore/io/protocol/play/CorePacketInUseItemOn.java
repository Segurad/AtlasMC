package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.inventory.EquipmentSlot;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInUseItemOn;
import io.netty.buffer.ByteBuf;

public class CorePacketInUseItemOn implements PacketIO<PacketInUseItemOn> {

	@Override
	public void read(PacketInUseItemOn packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.hand = readVarInt(in) == 0 ? EquipmentSlot.MAIN_HAND : EquipmentSlot.OFF_HAND;
		packet.position = in.readLong();
		packet.face = CorePacketInPlayerAction.FACES.get(readVarInt(in));
		packet.cursorPosX = in.readFloat();
		packet.cursorPosY = in.readFloat();
		packet.cursorPosZ = in.readFloat();	
		packet.insideBlock = in.readBoolean();
		packet.worldBorderHit = in.readBoolean();
		packet.sequence = readVarInt(in);
	}

	@Override
	public void write(PacketInUseItemOn packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.hand == EquipmentSlot.MAIN_HAND ? 0 : 1, out);
		out.writeLong(packet.position);
		writeVarInt(CorePacketInPlayerAction.FACES.indexOf(packet.face), out);
		out.writeFloat(packet.cursorPosX);
		out.writeFloat(packet.cursorPosY);
		out.writeFloat(packet.cursorPosZ);
		out.writeBoolean(packet.insideBlock);
		out.writeBoolean(packet.worldBorderHit);
		writeVarInt(packet.sequence, out);
	}

	@Override
	public PacketInUseItemOn createPacketData() {
		return new PacketInUseItemOn();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInUseItemOn.class);
	}

}
