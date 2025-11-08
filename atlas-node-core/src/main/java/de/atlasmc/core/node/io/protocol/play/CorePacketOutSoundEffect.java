package de.atlasmc.core.node.io.protocol.play;

import de.atlasmc.io.PacketUtil;
import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.SoundCategory;
import de.atlasmc.node.io.protocol.play.PacketOutSoundEffect;
import de.atlasmc.node.sound.EnumSound;
import de.atlasmc.node.sound.ResourceSound;
import de.atlasmc.util.enums.EnumUtil;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSoundEffect implements PacketIO<PacketOutSoundEffect> {

	@Override
	public void read(PacketOutSoundEffect packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.sound = PacketUtil.readVarIntEnumOrCodec(in, EnumSound.class, ResourceSound.STREAM_CODEC, handler.getCodecContext());
		packet.category = EnumUtil.getByID(SoundCategory.class, PacketUtil.readVarInt(in));
		packet.x = in.readInt()/8;
		packet.y = in.readInt()/8;
		packet.z = in.readInt()/8;
		packet.volume = in.readFloat();
		packet.pitch = in.readFloat();
		packet.seed = in.readLong();
	}

	@Override
	public void write(PacketOutSoundEffect packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		PacketUtil.writeVarIntOrCodec(packet.sound, out, ResourceSound.STREAM_CODEC, handler.getCodecContext());
		PacketUtil.writeVarInt(packet.category.getID(), out);
		out.writeInt((int) (packet.x*8));
		out.writeInt((int) (packet.y*8));
		out.writeInt((int) (packet.z*8));
		out.writeFloat(packet.volume);
		out.writeFloat(packet.pitch);
		out.writeLong(packet.seed);
	}
	
	@Override
	public PacketOutSoundEffect createPacketData() {
		return new PacketOutSoundEffect();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSoundEffect.class);
	}

}
