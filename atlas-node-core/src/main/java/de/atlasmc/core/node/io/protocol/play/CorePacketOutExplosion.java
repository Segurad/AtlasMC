package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import static de.atlasmc.io.PacketUtil.*;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutExplosion;
import de.atlasmc.node.sound.Sound;
import de.atlasmc.node.world.particle.Particle;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class CorePacketOutExplosion implements PacketIO<PacketOutExplosion> {

	@Override
	public void read(PacketOutExplosion packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.x = in.readDouble();
		packet.y = in.readDouble();
		packet.z = in.readDouble();
		packet.radius = in.readFloat();
		packet.blockCount = in.readInt();
		if (in.readBoolean()) {
			packet.motionX = in.readDouble();
			packet.motionY = in.readDouble();
			packet.motionZ = in.readDouble();
		}
		final CodecContext context = handler.getCodecContext();
		packet.particle = Particle.STREAM_CODEC.deserialize(in, context);
		packet.sound = Sound.STREAM_CODEC.deserialize(in, context);
		// TODO particle alternatives
		final int count = readVarInt(in);
		if (count == 0)
			return;
		for (int i = 0; i < count; i++) {
			Particle.STREAM_CODEC.deserialize(in, context);
			in.readFloat(); // scaling
			in.readFloat(); // speed
			readVarInt(in); // weight
		}
	}

	@Override
	public void write(PacketOutExplosion packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeDouble(packet.x);
		out.writeDouble(packet.y);
		out.writeDouble(packet.z);
		out.writeFloat(packet.radius);
		out.writeInt(packet.blockCount);
		if (packet.hasMotion()) {
			out.writeBoolean(true);
			out.writeDouble(packet.motionX);
			out.writeDouble(packet.motionY);
			out.writeDouble(packet.motionZ);
		} else {
			out.writeBoolean(false);
		}
		final CodecContext context = handler.getCodecContext();
		packet.particle.writeToStream(out, context);
		Sound.STREAM_CODEC.serialize(packet.sound, out, context);
		writeVarInt(0, out); // TODO particle alternatives
	}
	
	@Override
	public PacketOutExplosion createPacketData() {
		return new PacketOutExplosion();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutExplosion.class);
	}

}
