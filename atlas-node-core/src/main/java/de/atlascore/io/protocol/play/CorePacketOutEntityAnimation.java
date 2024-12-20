package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.entity.Entity.Animation;
import static de.atlasmc.io.protocol.ProtocolUtil.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutEntityAnimation;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityAnimation implements PacketIO<PacketOutEntityAnimation> {

	@Override
	public void read(PacketOutEntityAnimation packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.entityID = readVarInt(in);
		packet.animation = Animation.getByID(in.readUnsignedByte());
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
