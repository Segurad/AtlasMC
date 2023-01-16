package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutEntityEffect;
import de.atlasmc.potion.PotionEffectType;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityEffect extends PacketIO<PacketOutEntityEffect> {

	@Override
	public void read(PacketOutEntityEffect packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
		packet.setEffect(PotionEffectType.getByID(in.readByte()));
		packet.setAmplifier(in.readByte());
		packet.setDuration(readVarInt(in));
		packet.setFlags(in.readByte());
	}

	@Override
	public void write(PacketOutEntityEffect packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		out.writeByte(packet.getEffect().getID());
		out.writeByte(packet.getAmplifier());
		writeVarInt(packet.getDuration(), out);
		out.writeByte(packet.getFlags());
	}

	@Override
	public PacketOutEntityEffect createPacketData() {
		return new PacketOutEntityEffect();
	}

}
