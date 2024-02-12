package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutSoundEffect;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSoundEffect implements PacketIO<PacketOutSoundEffect> {

	@Override
	public void read(PacketOutSoundEffect packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		int soundID = readVarInt(in);
		if (soundID != 0) {
			packet.setSound(Sound.getByID(soundID-1));
		} else {
			packet.setIdentifier(readString(in));
			packet.setFixedRange(in.readBoolean());
			if (packet.hasFixedRange())
				packet.setRange(in.readFloat());
		}
		packet.setCategory(SoundCategory.getByID(readVarInt(in)));
		packet.setX(in.readInt()/8);
		packet.setY(in.readInt()/8);
		packet.setZ(in.readInt()/8);
		packet.setVolume(in.readFloat());
		packet.setPitch(in.readFloat());
		packet.setSeed(in.readLong());
	}

	@Override
	public void write(PacketOutSoundEffect packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		Sound sound = packet.getSound();
		if (sound != null) {
			writeVarInt(sound.getID()+1, out);
		} else {
			writeVarInt(0, out);
			writeString(packet.getIdentifier(), out);
			out.writeBoolean(packet.hasFixedRange());
			if (packet.hasFixedRange())
				out.writeFloat(packet.getRange());
		}
		writeVarInt(packet.getCategory().getID(), out);
		out.writeInt((int) (packet.getX()*8));
		out.writeInt((int) (packet.getY()*8));
		out.writeInt((int) (packet.getZ()*8));
		out.writeFloat(packet.getVolume());
		out.writeFloat(packet.getPitch());
		out.writeLong(packet.getSeed());
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
