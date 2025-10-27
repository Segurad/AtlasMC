package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutCombatDeath;
import io.netty.buffer.ByteBuf;

public class CorePacketOutCombatDeath implements PacketIO<PacketOutCombatDeath> {

	@Override
	public void read(PacketOutCombatDeath packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.playerID = readVarInt(in);
		packet.deathMessage = Chat.STREAM_CODEC.deserialize(in, con.getCodecContext());
	}

	@Override
	public void write(PacketOutCombatDeath packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.playerID, out);
		Chat.STREAM_CODEC.serialize(packet.deathMessage, out, con.getCodecContext());
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
