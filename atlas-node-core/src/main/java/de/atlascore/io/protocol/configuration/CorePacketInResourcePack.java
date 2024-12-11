package de.atlascore.io.protocol.configuration;

import java.io.IOException;

import de.atlasmc.event.player.PlayerResourcePackStatusEvent.ResourcePackStatus;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.configuration.PacketInResourcePack;
import io.netty.buffer.ByteBuf;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

public class CorePacketInResourcePack implements PacketIO<PacketInResourcePack> {

	@Override
	public void read(PacketInResourcePack packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.status = ResourcePackStatus.getByID(readVarInt(in));
	}

	@Override
	public void write(PacketInResourcePack packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeVarInt(packet.status.getID(), out);
	}

	@Override
	public PacketInResourcePack createPacketData() {
		return new PacketInResourcePack();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInResourcePack.class);
	}

}
