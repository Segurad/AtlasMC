package de.atlasmc.core.node.io.protocol.configuration;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.event.player.PlayerResourcePackStatusEvent.ResourcePackStatus;
import de.atlasmc.node.io.protocol.configuration.ServerboundResourcePack;
import de.atlasmc.util.EnumUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketInResourcePack implements PacketIO<ServerboundResourcePack> {

	@Override
	public void read(ServerboundResourcePack packet, ByteBuf in, ConnectionHandler con) throws IOException {
		packet.uuid = readUUID(in);
		packet.status = EnumUtil.getByID(ResourcePackStatus.class, readVarInt(in));
	}

	@Override
	public void write(ServerboundResourcePack packet, ByteBuf out, ConnectionHandler con) throws IOException {
		writeUUID(packet.uuid, out);
		writeVarInt(packet.status.getID(), out);
	}

	@Override
	public ServerboundResourcePack createPacketData() {
		return new ServerboundResourcePack();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(ServerboundResourcePack.class);
	}

}
