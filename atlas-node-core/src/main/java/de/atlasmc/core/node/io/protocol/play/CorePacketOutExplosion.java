package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.readVarInt;
import static de.atlasmc.node.io.protocol.ProtocolUtil.writeVarInt;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.entity.data.MetaDataType;
import de.atlasmc.node.io.protocol.ProtocolUtil;
import de.atlasmc.node.io.protocol.play.PacketOutExplosion;
import de.atlasmc.node.world.particle.ParticleType;
import io.netty.buffer.ByteBuf;

public class CorePacketOutExplosion implements PacketIO<PacketOutExplosion> {

	@Override
	public void read(PacketOutExplosion packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.x = in.readFloat();
		packet.y = in.readFloat();
		packet.z = in.readFloat();
		packet.strength = in.readFloat();
		final int count = in.readInt()*3;
		byte[] records = new byte[count];
		in.readBytes(records);
		packet.records = records;
		packet.motionX = in.readFloat();
		packet.motionY = in.readFloat();
		packet.motionZ = in.readFloat();
		packet.blockInteraction = readVarInt(in);
		packet.smallExplosionParticle = ParticleType.getByID(readVarInt(in));
		packet.smallExplosionParticleData = MetaDataType.PARTICLE.read(packet.smallExplosionParticle, in);
		packet.largeExplosionParticle = ParticleType.getByID(readVarInt(in));
		packet.largeExplosionParticleData = MetaDataType.PARTICLE.read(packet.smallExplosionParticle, in);
		packet.explosionSound = ProtocolUtil.readSound(in);
	}

	@Override
	public void write(PacketOutExplosion packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		out.writeFloat(packet.x);
		out.writeFloat(packet.y);
		out.writeFloat(packet.z);
		out.writeFloat(packet.strength);
		out.writeInt(packet.records.length/3);
		out.writeBytes(packet.records);
		out.writeFloat(packet.motionX);
		out.writeFloat(packet.motionY);
		out.writeFloat(packet.motionZ);
		writeVarInt(packet.blockInteraction, out);
		MetaDataType.PARTICLE.write(packet.smallExplosionParticle, packet.smallExplosionParticleData, false, out);
		MetaDataType.PARTICLE.write(packet.largeExplosionParticle, packet.largeExplosionParticleData, false, out);
		ProtocolUtil.writeSound(packet.explosionSound, out);
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
