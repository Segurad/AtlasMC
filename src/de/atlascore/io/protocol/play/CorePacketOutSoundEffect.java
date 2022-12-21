package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.CoreAbstractHandler;
import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.protocol.play.PacketOutSoundEffect;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSoundEffect extends CoreAbstractHandler<PacketOutSoundEffect> {

	@Override
	public void read(PacketOutSoundEffect packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setSound(Sound.getByID(readVarInt(in)));
		packet.setCategory(SoundCategory.getByID(readVarInt(in)));
		packet.setX(in.readInt()/8);
		packet.setY(in.readInt()/8);
		packet.setZ(in.readInt()/8);
		packet.setVolume(in.readFloat());
		packet.setPitch(in.readFloat());
	}

	@Override
	public void write(PacketOutSoundEffect packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getSound().getID(), out);
		writeVarInt(packet.getCategory().getID(), out);
		out.writeInt((int) (packet.getX()*8));
		out.writeInt((int) (packet.getY()*8));
		out.writeInt((int) (packet.getZ()*8));
		out.writeFloat(packet.getVolume());
		out.writeFloat(packet.getPitch());
	}
	
	@Override
	public PacketOutSoundEffect createPacketData() {
		return new PacketOutSoundEffect();
	}

}
