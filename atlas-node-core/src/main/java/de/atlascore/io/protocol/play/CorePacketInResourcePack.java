package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.event.player.PlayerResourcePackStatusEvent.ResourcePackStatus;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInResourcePack;
import io.netty.buffer.ByteBuf;

public class CorePacketInResourcePack implements PacketIO<PacketInResourcePack> {
	
	@Override
	public void read(PacketInResourcePack packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setStatus(ResourcePackStatus.getByID(readVarInt(in)));
	}

	@Override
	public void write(PacketInResourcePack packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getStatus().getID(), out);
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
