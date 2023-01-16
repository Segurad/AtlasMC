package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketInSteerBoat;
import io.netty.buffer.ByteBuf;

public class CorePacketInSteerBoat extends PacketIO<PacketInSteerBoat> {

	@Override
	public void read(PacketInSteerBoat packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setLeftPaddle(in.readBoolean());
		packet.setRightPaddle(in.readBoolean());
	}

	@Override
	public void write(PacketInSteerBoat packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeBoolean(packet.getLeftPaddleTurning());
		out.writeBoolean(packet.getRightPaddleTurning());
	}

	@Override
	public PacketInSteerBoat createPacketData() {
		return new PacketInSteerBoat();
	}

}
