package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.SoundCategory;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutStopSound;
import io.netty.buffer.ByteBuf;

public class CorePacketOutStopSound extends AbstractPacket implements PacketOutStopSound {

	private SoundCategory category;
	private String sound;
	
	public CorePacketOutStopSound() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutStopSound(SoundCategory category, String sound) {
		this();
		this.category = category;
		this.sound = sound;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		int flags = in.readByte();
		if (flags == 0) return;
		if (flags == 1 || flags == 3) {
			category = SoundCategory.getByID(readVarInt(in));
		}
		if (flags == 2 || flags == 3) {
			sound = readString(in);
		}
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		if (category != null) {
			if (sound != null) {
				out.writeByte(3);
				writeVarInt(category.getID(), out);
				writeString(sound, out);
			} else {
				out.writeByte(1);
				writeVarInt(category.ordinal(), out);
			}
		} else if (sound != null) {
			out.writeByte(2);
			writeString(sound, out);
		} else out.writeByte(0);
	}

	@Override
	public SoundCategory getCategory() {
		return category;
	}

	@Override
	public String getSound() {
		return sound;
	}

	@Override
	public void setCategory(SoundCategory category) {
		this.category = category;
	}

	@Override
	public void setSound(String sound) {
		this.sound = sound;
	}

}
