package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutParticle;
import de.atlasmc.node.world.particle.Particle;
import io.netty.buffer.ByteBuf;

public class CorePacketOutParticle implements PacketIO<PacketOutParticle> {

	@Override
	public void read(PacketOutParticle packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.longDistance = in.readBoolean();
		packet.x = in.readDouble();
		packet.y = in.readDouble();
		packet.z = in.readDouble();
		packet.offX = in.readFloat();
		packet.offY = in.readFloat();
		packet.offZ = in.readFloat();
		packet.maxSpeed = in.readFloat();
		packet.count = in.readInt();
		packet.particle = Particle.STREAM_CODEC.deserialize(in, handler.getCodecContext());
	}

	@Override
	public void write(PacketOutParticle packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeBoolean(packet.longDistance);
		out.writeDouble(packet.x);
		out.writeDouble(packet.y);
		out.writeDouble(packet.z);
		out.writeFloat(packet.offX);
		out.writeFloat(packet.offY);
		out.writeFloat(packet.offZ);
		out.writeFloat(packet.maxSpeed);
		out.writeInt(packet.count);
		packet.particle.writeToStream(out, handler.getCodecContext());
	}

	@Override
	public PacketOutParticle createPacketData() {
		return new PacketOutParticle();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutParticle.class);
	};

}
