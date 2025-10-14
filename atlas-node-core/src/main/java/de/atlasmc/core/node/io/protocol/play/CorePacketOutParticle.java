package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.entity.data.MetaDataType;
import de.atlasmc.node.io.protocol.play.PacketOutParticle;
import de.atlasmc.node.world.particle.ParticleType;
import de.atlasmc.util.EnumUtil;
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
		packet.particle = EnumUtil.getByID(ParticleType.class, readVarInt(in));
		packet.data = MetaDataType.PARTICLE.read(packet.particle, in);
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
		MetaDataType.PARTICLE.write(packet.particle, packet.data, false, out);
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
