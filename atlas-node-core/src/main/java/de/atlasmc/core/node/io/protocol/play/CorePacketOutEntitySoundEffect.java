package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.SoundCategory;
import de.atlasmc.node.io.protocol.ProtocolUtil;
import de.atlasmc.node.io.protocol.play.PacketOutEntitySoundEffect;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntitySoundEffect implements PacketIO<PacketOutEntitySoundEffect> {
	
	@Override
	public void read(PacketOutEntitySoundEffect packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.sound = ProtocolUtil.readSound(in);
		packet.category = SoundCategory.getByID(readVarInt(in));
		packet.entityID = readVarInt(in);
		packet.volume = in.readFloat();
		packet.pitch = in.readFloat();
		packet.seed = in.readLong();
	}

	@Override
	public void write(PacketOutEntitySoundEffect packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		ProtocolUtil.writeSound(packet.sound, out);
		writeVarInt(packet.category.getID(), out);
		writeVarInt(packet.entityID, out);
		out.writeFloat(packet.volume);
		out.writeFloat(packet.pitch);
		out.writeLong(packet.seed);
	}
	
	@Override
	public PacketOutEntitySoundEffect createPacketData() {
		return new PacketOutEntitySoundEffect();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutEntitySoundEffect.class);
	}

}
