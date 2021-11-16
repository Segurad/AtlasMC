package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.Sound;
import de.atlasmc.SoundCategory;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutSoundEffect;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSoundEffect extends AbstractPacket implements PacketOutSoundEffect {

	private int soundID, category, x, y, z;
	private float volume, pitch;
	
	public CorePacketOutSoundEffect() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutSoundEffect(int soundID, SoundCategory category, double x, double y, double z, float volume, float pitch) {
		this();
		this.soundID = soundID;
		this.category = category.ordinal();
		setPosition(x, y, z);
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		soundID = readVarInt(in);
		category = readVarInt(in);
		x = in.readInt();
		y = in.readInt();
		z = in.readInt();
		volume = in.readFloat();
		pitch = in.readFloat();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(soundID, out);
		writeVarInt(category, out);
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(z);
		out.writeFloat(volume);
		out.writeFloat(pitch);
	}

	@Override
	public SoundCategory getCategory() {
		return SoundCategory.getByID(category);
	}

	@Override
	public double getX() {
		return x/8;
	}

	@Override
	public double getY() {
		return y/8;
	}

	@Override
	public double getZ() {
		return z/8;
	}

	@Override
	public float getPitch() {
		return pitch;
	}

	@Override
	public Sound getSound() {
		return Sound.getByID(soundID);
	}

	@Override
	public float getVolume() {
		return volume;
	}

	@Override
	public void setPosition(double x, double y, double z) {
		this.x = (int) (x * 8);
		this.y = (int) (y * 8);
		this.z = (int) (z * 8);
	}

	@Override
	public void setCategory(SoundCategory category) {
		this.category = category.getID();
	}

	@Override
	public void setSound(Sound sound) {
		this.soundID = sound.getID();
	}

	@Override
	public void setVolume(float volume) {
		this.volume = volume;
	}

	@Override
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

}
