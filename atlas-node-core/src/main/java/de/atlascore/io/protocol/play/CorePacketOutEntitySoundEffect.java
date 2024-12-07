package de.atlascore.io.protocol.play;

import static de.atlasmc.io.AbstractPacket.readIdentifier;
import static de.atlasmc.io.AbstractPacket.readVarInt;
import static de.atlasmc.io.AbstractPacket.writeString;
import static de.atlasmc.io.AbstractPacket.writeVarInt;

import java.io.IOException;

import de.atlasmc.NamespacedKey;
import de.atlasmc.SoundCategory;
import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutEntitySoundEffect;
import de.atlasmc.sound.EnumSound;
import de.atlasmc.sound.ResourceSound;
import de.atlasmc.sound.Sound;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntitySoundEffect implements PacketIO<PacketOutEntitySoundEffect> {
	
	@Override
	public void read(PacketOutEntitySoundEffect packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		int soundID = readVarInt(in);
		if (soundID > 0) {
			packet.sound = EnumSound.getByID(soundID-1);
		} else {
			NamespacedKey key = readIdentifier(in);
			float fixedRange = Float.NaN;
			if (in.readBoolean())
				fixedRange = in.readFloat();
			packet.sound = new ResourceSound(key, fixedRange);
		}
		packet.category = SoundCategory.getByID(readVarInt(in));
		packet.entityID = readVarInt(in);
		packet.volume = in.readFloat();
		packet.pitch = in.readFloat();
		packet.seed = in.readLong();
	}

	@Override
	public void write(PacketOutEntitySoundEffect packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		Sound sound = packet.sound;
		if (sound instanceof EnumSound enumSound) {
			writeVarInt(enumSound.getID()+1, out);
		} else {
			writeVarInt(0, out);
			writeString(sound.getNamespacedKeyRaw(), out);
			float fixedRange = sound.getFixedRange();
			if (fixedRange != fixedRange) {
				out.writeBoolean(false);
			} else {
				out.writeBoolean(true);
				out.writeFloat(fixedRange);
			}
		}
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
