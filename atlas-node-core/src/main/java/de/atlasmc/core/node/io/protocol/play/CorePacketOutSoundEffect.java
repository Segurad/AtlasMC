package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.writeVarInt;
import static de.atlasmc.node.io.protocol.ProtocolUtil.readSound;
import static de.atlasmc.node.io.protocol.ProtocolUtil.writeSound;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketOutSoundEffect;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSoundEffect implements PacketIO<PacketOutSoundEffect> {

	@Override
	public void read(PacketOutSoundEffect packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.sound = readSound(in);
		packet.x = in.readInt()/8;
		packet.y = in.readInt()/8;
		packet.z = in.readInt()/8;
		packet.volume = in.readFloat();
		packet.pitch = in.readFloat();
		packet.seed = in.readLong();
	}

	@Override
	public void write(PacketOutSoundEffect packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeSound(packet.sound, out);
		writeVarInt(packet.category.getID(), out);
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
