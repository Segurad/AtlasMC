package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutEntitySoundEffect;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntitySoundEffect extends CoreAbstractHandler<PacketOutEntitySoundEffect> {
	
	@Override
	public void read(PacketOutEntitySoundEffect packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setSound(Sound.getByID(readVarInt(in)));
		packet.setCategory(SoundCategory.getByID(readVarInt(in)));
		packet.setEntityID(readVarInt(in));
		packet.setVolume(in.readFloat());
		packet.setPitch(in.readFloat());
	}

	@Override
	public void write(PacketOutEntitySoundEffect packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getSound().getID(), out);
		writeVarInt(packet.getCategory().getID(), out);
		writeVarInt(packet.getEntityID(), out);
		out.writeFloat(packet.getVolume());
		out.writeFloat(packet.getPitch());
	}
	
	@Override
	public PacketOutEntitySoundEffect createPacketData() {
		return new PacketOutEntitySoundEffect();
	}

}
