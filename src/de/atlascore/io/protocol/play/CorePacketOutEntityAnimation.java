package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.entity.Entity.Animation;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutEntityAnimation;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityAnimation extends PacketIO<PacketOutEntityAnimation> {

	@Override
	public void read(PacketOutEntityAnimation packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
		packet.setAnimation(Animation.getByID(in.readUnsignedByte()));
	}

	@Override
	public void write(PacketOutEntityAnimation packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		out.writeByte(packet.getAnimation().getID());
	}

	@Override
	public PacketOutEntityAnimation createPacketData() {
		return new PacketOutEntityAnimation();
	}

}
