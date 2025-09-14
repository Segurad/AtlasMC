package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.readVarInt;
import static de.atlasmc.io.PacketUtil.writeVarInt;

import java.io.IOException;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.node.io.protocol.play.PacketOutEntityEffect;
import de.atlasmc.node.potion.PotionEffectType;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityEffect implements PacketIO<PacketOutEntityEffect> {

	@Override
	public void read(PacketOutEntityEffect packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.entityID = readVarInt(in);
		packet.effect = PotionEffectType.getByID(in.readByte());
		packet.amplifier = in.readByte();
		packet.duration = readVarInt(in);
		packet.flags = in.readByte();
	}

	@Override
	public void write(PacketOutEntityEffect packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.entityID, out);
		out.writeByte(packet.effect.getID());
		out.writeByte(packet.amplifier);
		writeVarInt(packet.duration, out);
		out.writeByte(packet.flags);
	}

	@Override
	public PacketOutEntityEffect createPacketData() {
		return new PacketOutEntityEffect();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutEntityEffect.class);
	}

}
