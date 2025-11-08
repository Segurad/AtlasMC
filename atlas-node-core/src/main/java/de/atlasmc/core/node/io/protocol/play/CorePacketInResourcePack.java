package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.event.player.PlayerResourcePackStatusEvent.ResourcePackStatus;
import de.atlasmc.node.io.protocol.play.PacketInResourcePack;
import de.atlasmc.util.enums.EnumUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketInResourcePack implements PacketIO<PacketInResourcePack> {
	
	@Override
	public void read(PacketInResourcePack packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.status = EnumUtil.getByID(ResourcePackStatus.class, readVarInt(in));
	}

	@Override
	public void write(PacketInResourcePack packet, ByteBuf out, ConnectionHandler handler) throws IOException {
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
