package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketInPaddleBoat;
import io.netty.buffer.ByteBuf;

public class CorePacketInPaddleBoat implements PacketCodec<PacketInPaddleBoat> {

	@Override
	public void deserialize(PacketInPaddleBoat packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.leftPaddle = in.readBoolean();
		packet.rightPaddle = in.readBoolean();
	}

	@Override
	public void serialize(PacketInPaddleBoat packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeBoolean(packet.leftPaddle);
		out.writeBoolean(packet.rightPaddle);
	}

	@Override
	public PacketInPaddleBoat createPacketData() {
		return new PacketInPaddleBoat();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketInPaddleBoat.class);
	}

}
