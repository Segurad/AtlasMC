package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutSetExperiance;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetExperience implements PacketIO<PacketOutSetExperiance> {

	@Override
	public void read(PacketOutSetExperiance packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.experienceBar = in.readFloat();
		packet.level = readVarInt(in);
		packet.totalExperience = readVarInt(in);
	}

	@Override
	public void write(PacketOutSetExperiance packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeFloat(packet.experienceBar);
		writeVarInt(packet.level, out);
		writeVarInt(packet.totalExperience, out);
	}
	
	@Override
	public PacketOutSetExperiance createPacketData() {
		return new PacketOutSetExperiance();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetExperiance.class);
	}

}
