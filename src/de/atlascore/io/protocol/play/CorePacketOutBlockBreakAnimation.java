package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutBlockBreakAnimation;
import io.netty.buffer.ByteBuf;

public class CorePacketOutBlockBreakAnimation extends CoreAbstractHandler<PacketOutBlockBreakAnimation> {

	@Override
	public void read(PacketOutBlockBreakAnimation packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
		packet.setPosition(in.readLong());
		packet.setStage(in.readByte());
	}

	@Override
	public void write(PacketOutBlockBreakAnimation packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		out.writeLong(packet.getPosition());
		out.writeByte(packet.getStage());
	}

	@Override
	public PacketOutBlockBreakAnimation createPacketData() {
		return new PacketOutBlockBreakAnimation();
	}

}
