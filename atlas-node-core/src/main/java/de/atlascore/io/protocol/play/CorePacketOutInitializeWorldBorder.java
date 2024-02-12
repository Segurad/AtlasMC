package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutInitializeWorldBorder;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.AbstractPacket.*;

public class CorePacketOutInitializeWorldBorder implements PacketIO<PacketOutInitializeWorldBorder> {

	@Override
	public void read(PacketOutInitializeWorldBorder packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.x = in.readDouble();
		packet.z = in.readDouble();
		packet.oldDiameter = in.readDouble();
		packet.newDiameter = in.readDouble();
		packet.speed = readVarLong(in);
		packet.portalTeleportBoundary = readVarInt(in);
		packet.warningBlocks = readVarInt(in);
		packet.warningTime = readVarInt(in);
	}

	@Override
	public void write(PacketOutInitializeWorldBorder packet, ByteBuf out, ConnectionHandler con) throws IOException {
		out.writeDouble(packet.x);
		out.writeDouble(packet.z);
		out.writeDouble(packet.oldDiameter);
		out.writeDouble(packet.newDiameter);
		writeVarLong(packet.speed, out);
		writeVarInt(packet.portalTeleportBoundary, out);
		writeVarInt(packet.warningBlocks, out);
		writeVarInt(packet.warningTime, out);
	}

	@Override
	public PacketOutInitializeWorldBorder createPacketData() {
		return new PacketOutInitializeWorldBorder();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutInitializeWorldBorder.class);
	}

}
