package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.inventory.EquipmentSlot;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInUseItemOn;
import io.netty.buffer.ByteBuf;

public class CorePacketInUseItemOn implements PacketIO<PacketInUseItemOn> {

	@Override
	public void read(PacketInUseItemOn packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setHand(readVarInt(in) == 0 ? EquipmentSlot.MAIN_HAND : EquipmentSlot.OFF_HAND);
		packet.setPosition(in.readLong());
		packet.setFace(CorePacketInPlayerAction.FACES.get(readVarInt(in)));
		packet.setCursurPositionX(in.readFloat());
		packet.setCursurPositionY(in.readFloat());
		packet.setCursurPositionZ(in.readFloat());	
		packet.setInsideblock(in.readBoolean());
		packet.setSequence(readVarInt(in));
	}

	@Override
	public void write(PacketInUseItemOn packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.getHand() == EquipmentSlot.MAIN_HAND ? 0 : 1, out);
		out.writeLong(packet.getPosition());
		writeVarInt(CorePacketInPlayerAction.FACES.indexOf(packet.getFace()), out);
		out.writeFloat(packet.getCursurPositionX());
		out.writeFloat(packet.getCursurPositionY());
		out.writeFloat(packet.getCursurPositionZ());
		out.writeBoolean(packet.isInsideblock());
		writeVarInt(packet.getSequence(), out);
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
