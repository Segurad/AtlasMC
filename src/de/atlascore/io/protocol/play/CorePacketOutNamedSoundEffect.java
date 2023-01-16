package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.SoundCategory;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutNamedSoundEffect;
import io.netty.buffer.ByteBuf;

public class CorePacketOutNamedSoundEffect extends PacketIO<PacketOutNamedSoundEffect> {

	@Override
	public void read(PacketOutNamedSoundEffect packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setIdentifier(readString(in));
		packet.setCategory(SoundCategory.getByID(readVarInt(in)));
		packet.setX(in.readInt()/8);
		packet.setY(in.readInt());
		packet.setZ(in.readInt());
		packet.setVolume(in.readFloat());
		packet.setPitch(in.readFloat());
	}

	@Override
	public void write(PacketOutNamedSoundEffect packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeString(packet.getIdentifier(), out);
		writeVarInt(packet.getCategory().getID(), out);
		out.writeInt((int) (packet.getX()*8));
		out.writeInt((int) (packet.getY()*8));
		out.writeInt((int) (packet.getZ()*8));
		out.writeFloat(packet.getVolume());
		out.writeFloat(packet.getPitch());
	}

	@Override
	public PacketOutNamedSoundEffect createPacketData() {
		return new PacketOutNamedSoundEffect();
	}

}
