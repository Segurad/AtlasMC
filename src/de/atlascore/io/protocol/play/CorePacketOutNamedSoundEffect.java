package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.SoundCategory;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutNamedSoundEffect;
import io.netty.buffer.ByteBuf;

public class CorePacketOutNamedSoundEffect extends AbstractPacket implements PacketOutNamedSoundEffect {

	private String identifier;
	private int category, x, y, z;
	private float volume, pitch;
	
	public CorePacketOutNamedSoundEffect() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutNamedSoundEffect(String identifier, SoundCategory category, double x, double y, double z, float volume, float pitch) {
		this();
		this.identifier = identifier;
		this.category = category.ordinal();
		setPosition(x, y, z);
		this.volume = volume;
		this.pitch = pitch;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		identifier = readString(in);
		category = readVarInt(in);
		x = in.readInt();
		y = in.readInt();
		z = in.readInt();
		volume = in.readFloat();
		pitch = in.readFloat();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeString(identifier, out);
		writeVarInt(category, out);
		out.writeInt(x);
		out.writeInt(y);
		out.writeInt(z);
		out.writeFloat(volume);
		out.writeFloat(pitch);
	}

	@Override
	public String getIdentifier() {
		return identifier;
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
	public float getVolume() {
		return volume;
	}

	@Override
	public float getPitch() {
		return pitch;
	}

	@Override
	public void setPosition(double x, double y, double z) {
		this.x = (int) (x*8);
		this.y = (int) (y*8);
		this.z = (int) (z*8);
	}

	@Override
	public void setCategory(SoundCategory category) {
		this.category = category.getID();
	}

	@Override
	public void setIdentifier(String sound) {
		this.identifier = sound;
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
