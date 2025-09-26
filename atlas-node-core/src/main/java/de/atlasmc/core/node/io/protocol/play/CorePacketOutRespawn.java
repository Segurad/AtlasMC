package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.readIdentifier;
import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeIdentifier;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.Gamemode;
import de.atlasmc.node.io.protocol.play.PacketOutRespawn;
import io.netty.buffer.ByteBuf;

public class CorePacketOutRespawn implements PacketIO<PacketOutRespawn> {

	@Override
	public void read(PacketOutRespawn packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.dimension = readVarInt(in);
		packet.world = readIdentifier(in);
		packet.seed = in.readLong();
		packet.gamemode = Gamemode.getByID(in.readUnsignedByte());
		int rawPreviousGamemode = readVarInt(in);
		if (rawPreviousGamemode != -1)
			packet.previous = Gamemode.getByID(rawPreviousGamemode);
		packet.debug = in.readBoolean();
		packet.flat = in.readBoolean();
		if (in.readBoolean()) {
			packet.deathDimension = readIdentifier(in);
			packet.deathLocation = in.readLong();
		}
		packet.portalCooldown = readVarInt(in);
		packet.dataKept = in.readByte();
	}

	@Override
	public void write(PacketOutRespawn packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.dimension, out);
		writeIdentifier(packet.world, out);
		out.writeLong(packet.seed);
		out.writeByte(packet.gamemode.getID());
		if (packet.previous != null) {
			out.writeByte(packet.previous.getID());
		} else {
			out.writeByte(-1);
		}
		out.writeBoolean(packet.debug);
		out.writeBoolean(packet.flat);
		if (packet.deathDimension != null) {
			out.writeBoolean(true);
			writeIdentifier(packet.deathDimension, out);
			out.writeLong(packet.deathLocation);
		} else {
			out.writeBoolean(false);
		}
		writeVarInt(packet.portalCooldown, out);
		out.writeByte(packet.dataKept);
	}
	
	@Override
	public PacketOutRespawn createPacketData() {
		return new PacketOutRespawn();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutRespawn.class);
	}

}
