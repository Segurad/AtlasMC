package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSpawnExperienceOrb;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSpawnExperienceOrb implements PacketIO<PacketOutSpawnExperienceOrb> {

	@Override
	public void read(PacketOutSpawnExperienceOrb packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
		packet.setX(in.readDouble());
		packet.setY(in.readDouble());
		packet.setZ(in.readDouble());
		packet.setExperience(in.readUnsignedShort());
	}

	@Override
	public void write(PacketOutSpawnExperienceOrb packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		out.writeDouble(packet.getX());
		out.writeDouble(packet.getY());
		out.writeDouble(packet.getZ());
		out.writeShort(packet.getExperience());
	}
	
	@Override
	public PacketOutSpawnExperienceOrb createPacketData() {
		return new PacketOutSpawnExperienceOrb();
	}
	
	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSpawnExperienceOrb.class);
	}

}
