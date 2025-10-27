package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.entity.Entity.Animation;
import de.atlasmc.node.io.protocol.play.PacketOutEntityAnimation;
import de.atlasmc.util.EnumUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityAnimation implements PacketIO<PacketOutEntityAnimation> {

	@Override
	public void read(PacketOutEntityAnimation packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.entityID = readVarInt(in);
		packet.animation = EnumUtil.getByID(Animation.class, in.readUnsignedByte());
	}

	@Override
	public void write(PacketOutEntityAnimation packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.entityID, out);
		out.writeByte(packet.animation.getID());
	}

	@Override
	public PacketOutEntityAnimation createPacketData() {
		return new PacketOutEntityAnimation();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutEntityAnimation.class);
	}

}
