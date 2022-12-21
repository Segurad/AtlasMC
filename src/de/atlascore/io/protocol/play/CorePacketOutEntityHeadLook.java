package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import static de.atlasmc.io.AbstractPacket.*;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutEntityHeadLook;
import de.atlasmc.util.MathUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityHeadLook extends CoreAbstractHandler<PacketOutEntityHeadLook> {

	@Override
	public void read(PacketOutEntityHeadLook packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
		packet.setYaw(MathUtil.fromAngle(in.readByte()));
	}

	@Override
	public void write(PacketOutEntityHeadLook packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		out.writeByte(MathUtil.toAngle(packet.getYaw()));
	}
	
	@Override
	public PacketOutEntityHeadLook createPacketData() {
		return new PacketOutEntityHeadLook();
	}

}
