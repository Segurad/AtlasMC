package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import de.atlasmc.Particle;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutParticle;
import io.netty.buffer.ByteBuf;

public class CorePacketOutParticle extends CoreAbstractHandler<PacketOutParticle> {

	@Override
	public void read(PacketOutParticle packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		Particle particle = Particle.getByID(in.readInt());
		packet.setParticle(particle);
		packet.setLongDistance(in.readBoolean());
		packet.setX(in.readDouble());
		packet.setY(in.readDouble());
		packet.setZ(in.readDouble());
		packet.setOffX(in.readFloat());
		packet.setOffY(in.readFloat());
		packet.setOffZ(in.readFloat());
		packet.setParticleData(in.readFloat());
		packet.setCount(in.readInt());
		packet.setData(MetaDataType.PARTICLE.read(particle, in));
	}

	@Override
	public void write(PacketOutParticle packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeInt(packet.getParticle().getID());
		out.writeBoolean(packet.isLongDistance());
		out.writeDouble(packet.getX());
		out.writeDouble(packet.getY());
		out.writeDouble(packet.getZ());
		out.writeFloat(packet.getOffX());
		out.writeFloat(packet.getOffY());
		out.writeFloat(packet.getOffZ());
		out.writeFloat(packet.getParticleData());
		out.writeInt(packet.getCount());
		MetaDataType.PARTICLE.write(packet.getParticle(), packet.getData(), false, out);
	}

	@Override
	public PacketOutParticle createPacketData() {
		return new PacketOutParticle();
	};

}
