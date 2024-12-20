package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSetCooldown;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetCooldown implements PacketIO<PacketOutSetCooldown> {

	@Override
	public void read(PacketOutSetCooldown packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.itemID = readVarInt(in);
		packet.cooldown = readVarInt(in);
	}

	@Override
	public void write(PacketOutSetCooldown packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.itemID, out);
		writeVarInt(packet.cooldown, out);
	}

	@Override
	public PacketOutSetCooldown createPacketData() {
		return new PacketOutSetCooldown();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetCooldown.class);
	}

}
