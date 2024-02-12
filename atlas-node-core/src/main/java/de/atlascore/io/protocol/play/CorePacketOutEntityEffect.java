package de.atlascore.io.protocol.play;

import java.io.IOException;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutEntityEffect;
import de.atlasmc.potion.PotionEffectType;
import de.atlasmc.util.nbt.io.NBTNIOReader;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityEffect implements PacketIO<PacketOutEntityEffect> {

	@Override
	public void read(PacketOutEntityEffect packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
		packet.setEffect(PotionEffectType.getByID(in.readByte()));
		packet.setAmplifier(in.readByte());
		packet.setDuration(readVarInt(in));
		packet.setFlags(in.readByte());
		if (in.readBoolean()) {
			NBTNIOReader reader = new NBTNIOReader(in, true);
			reader.skipToEnd(); // TODO read factor data
 			reader.close();
		}
	}

	@Override
	public void write(PacketOutEntityEffect packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		out.writeByte(packet.getEffect().getID());
		out.writeByte(packet.getAmplifier());
		writeVarInt(packet.getDuration(), out);
		out.writeByte(packet.getFlags());
		out.writeBoolean(false);
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
