package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutCombatDeath;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CorePacketOutCombatDeath implements PacketIO<PacketOutCombatDeath> {

	@Override
	public void read(PacketOutCombatDeath packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.playerID = readVarInt(in);
		packet.deathMessage = readTextComponent(in);
	}

	@Override
	public void write(PacketOutCombatDeath packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.playerID, out);
		writeTextComponent(packet.deathMessage, out);
	}

	@Override
	public PacketOutCombatDeath createPacketData() {
		return new PacketOutCombatDeath();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutCombatDeath.class);
	}

}
