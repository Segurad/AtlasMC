package de.atlasmc.core.node.io.protocol.play;

import java.io.IOException;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import static de.atlasmc.io.PacketUtil.*;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.entity.metadata.type.MetaDataType;
import de.atlasmc.node.io.protocol.play.PacketOutExplosion;
import de.atlasmc.node.sound.EnumSound;
import de.atlasmc.node.sound.ResourceSound;
import de.atlasmc.node.world.particle.ParticleType;
import de.atlasmc.util.codec.CodecContext;
import de.atlasmc.util.enums.EnumUtil;
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
		final CodecContext context = handler.getCodecContext();
		packet.smallExplosionParticle = EnumUtil.getByID(ParticleType.class, readVarInt(in));
		packet.smallExplosionParticleData = MetaDataType.PARTICLE.read(packet.smallExplosionParticle, in, context);
		packet.largeExplosionParticle = EnumUtil.getByID(ParticleType.class, readVarInt(in));
		packet.largeExplosionParticleData = MetaDataType.PARTICLE.read(packet.smallExplosionParticle, in, context);
		packet.explosionSound = readVarIntEnumOrCodec(in, EnumSound.class, ResourceSound.STREAM_CODEC, context);
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
		final CodecContext context = handler.getCodecContext();
		MetaDataType.PARTICLE.write(packet.smallExplosionParticle, packet.smallExplosionParticleData, false, out, context);
		MetaDataType.PARTICLE.write(packet.largeExplosionParticle, packet.largeExplosionParticleData, false, out, context);
		writeVarIntOrCodec(packet.explosionSound, out, ResourceSound.STREAM_CODEC, handler.getCodecContext());
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
