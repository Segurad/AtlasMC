package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutWindowConfirmation;
import io.netty.buffer.ByteBuf;

public class CorePacketOutWindowConfirmation extends CoreAbstractHandler<PacketOutWindowConfirmation> {

	@Override
	public void read(PacketOutWindowConfirmation packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setWindowID(in.readByte());
		packet.setActionNumber(in.readShort());
		packet.setAccepted(in.readBoolean());
	}

	@Override
	public void write(PacketOutWindowConfirmation packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeByte(packet.getWindowID());
		out.writeShort(packet.getActionNumber());
		out.writeBoolean(packet.isAccepted());
	}

	@Override
	public PacketOutWindowConfirmation createPacketData() {
		return new PacketOutWindowConfirmation();
	}

}
