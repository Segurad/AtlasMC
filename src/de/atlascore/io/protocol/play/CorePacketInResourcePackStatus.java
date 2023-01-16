package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.event.player.PlayerResourcePackStatusEvent.ResourcePackStatus;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInResourcePackStatus;
import io.netty.buffer.ByteBuf;

public class CorePacketInResourcePackStatus extends PacketIO<PacketInResourcePackStatus> {
	
	@Override
	public void read(PacketInResourcePackStatus packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setStatus(ResourcePackStatus.getByID(readVarInt(in)));
	}

	@Override
	public void write(PacketInResourcePackStatus packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getStatus().getID(), out);
	}
	
	@Override
	public PacketInResourcePackStatus createPacketData() {
		return new PacketInResourcePackStatus();
	}

}
