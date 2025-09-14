package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.readIdentifier;
import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeIdentifier;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.NamespacedKey;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.Gamemode;
import de.atlasmc.node.io.protocol.play.PacketOutLogin;
import io.netty.buffer.ByteBuf;

public class CorePacketOutLogin implements PacketIO<PacketOutLogin> {

	@Override
	public void read(PacketOutLogin packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.entityID = readVarInt(in);
		packet.hardcore = in.readBoolean();
		final int worldCount = readVarInt(in);
		if (worldCount > 0) {
			List<NamespacedKey> worlds = packet.worlds = new ArrayList<>(worldCount);
			for (int i = 0; i < worldCount; i++) {
				worlds.add(readIdentifier(in));
			}
		}
		packet.maxPlayers = readVarInt(in); // ignored value previous max players
		packet.viewDistance = readVarInt(in);
		packet.simulationDistance = readVarInt(in);
		packet.reducedDebugInfo = in.readBoolean();
		packet.respawnScreen = in.readBoolean();
		packet.limitedCrafting = in.readBoolean();
		packet.dimension = readVarInt(in);
		packet.world = readIdentifier(in);
		packet.seed = in.readLong();
		packet.gamemode = Gamemode.getByID(in.readUnsignedByte());
		int oldGamemode = in.readByte();
		if (oldGamemode != -1)
			packet.oldGamemode = Gamemode.getByID(oldGamemode);
		packet.debug = in.readBoolean();
		packet.flat = in.readBoolean();
		if (in.readBoolean()) {
			packet.deathWorld = readIdentifier(in);
			packet.deathPosition = in.readLong();
		}
		packet.portalCooldown = readVarInt(in);
	}

	@Override
	public void write(PacketOutLogin packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.entityID, out);
		out.writeBoolean(packet.hardcore);
		List<NamespacedKey> worlds = packet.worlds;
		if (worlds == null || worlds.isEmpty()) {
			writeVarInt(0, out);
		} else {
			writeVarInt(worlds.size(), out);
			for (NamespacedKey world : worlds) {
				writeIdentifier(world, out);
			}
		}
		writeVarInt(packet.maxPlayers, out); // ignored value was once max players (why mojang why is this value still in the packet)
		writeVarInt(packet.viewDistance, out);
		writeVarInt(packet.simulationDistance, out);
		out.writeBoolean(packet.reducedDebugInfo);
		out.writeBoolean(packet.respawnScreen);
		out.writeBoolean(packet.limitedCrafting);
		writeVarInt(packet.dimension, out);
		writeIdentifier(packet.world, out);
		out.writeLong(packet.seed);
		out.writeByte(packet.gamemode.getID());
		out.writeByte(packet.gamemode != null ? packet.gamemode.getID() : (byte) -1);
		out.writeBoolean(packet.debug);
		out.writeBoolean(packet.flat);
		out.writeBoolean(packet.deathWorld != null);
		if (packet.deathWorld != null) {
			writeIdentifier(packet.deathWorld, out);
			out.writeLong(packet.deathPosition);
		}
		writeVarInt(packet.portalCooldown, out);
	}

	@Override
	public PacketOutLogin createPacketData() {
		return new PacketOutLogin();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutLogin.class);
	}
	
}
