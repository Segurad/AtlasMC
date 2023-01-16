package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.inventory.EquipmentSlot;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInPlayerBlockPlacement;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerBlockPlacement extends PacketIO<PacketInPlayerBlockPlacement> {

	@Override
	public void read(PacketInPlayerBlockPlacement packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.setHand(readVarInt(in) == 0 ? EquipmentSlot.HAND : EquipmentSlot.OFF_HAND);
		packet.setFace(CorePacketInPlayerDigging.FACES.get(readVarInt(in)));
		packet.setPosition(in.readLong());
		packet.setCursurPositionX(in.readFloat());
		packet.setCursurPositionY(in.readFloat());
		packet.setCursurPositionZ(in.readFloat());	
	}

	@Override
	public void write(PacketInPlayerBlockPlacement packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.getHand() == EquipmentSlot.HAND ? 0 : 1, out);
		writeVarInt(CorePacketInPlayerDigging.FACES.indexOf(packet.getFace()), out);
		out.writeLong(packet.getPosition());
		out.writeFloat(packet.getCursurPositionX());
		out.writeFloat(packet.getCursurPositionY());
		out.writeFloat(packet.getCursurPositionZ());
	}

	@Override
	public PacketInPlayerBlockPlacement createPacketData() {
		return new PacketInPlayerBlockPlacement();
	}

}
