package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutSpawnExperienceOrb;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSpawnExperienceOrb implements PacketIO<PacketOutSpawnExperienceOrb> {

	@Override
	public void read(PacketOutSpawnExperienceOrb packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.entityID = readVarInt(in);
		packet.x = in.readDouble();
		packet.y = in.readDouble();
		packet.z = in.readDouble();
		packet.count = in.readUnsignedShort();
	}

	@Override
	public void write(PacketOutSpawnExperienceOrb packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.entityID, out);
		out.writeDouble(packet.x);
		out.writeDouble(packet.y);
		out.writeDouble(packet.z);
		out.writeShort(packet.count);
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
