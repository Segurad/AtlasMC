package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.SoundCategory;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutEntitySoundEffect;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntitySoundEffect extends AbstractPacket implements PacketOutEntitySoundEffect {

	private int soundID, category, entityID;
	private float volume, pitch;
	
	public CorePacketOutEntitySoundEffect() {
		super(CoreProtocolAdapter.VERSION);
	}

	public CorePacketOutEntitySoundEffect(int soundID, SoundCategory category, int entityID, float volume, float pitch) {
		this();
		this.soundID = soundID;
		this.category = category.ordinal();
		this.entityID = entityID;
		this.volume = volume;
		this.pitch = pitch;
	}
	
	@Override
	public void read(ByteBuf in) throws IOException {
		soundID = readVarInt(in);
		category = readVarInt(in);
		entityID = readVarInt(in);
		volume = in.readFloat();
		pitch = in.readFloat();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(soundID, out);
		writeVarInt(category, out);
		writeVarInt(entityID, out);
		out.writeFloat(volume);
		out.writeFloat(pitch);
	}

	@Override
	public int getSoundID() {
		return soundID;
	}

	@Override
	public SoundCategory getSoundCategory() {
		return SoundCategory.getByID(category);
	}

	@Override
	public int getEntityID() {
		return entityID;
	}

	@Override
	public float getVolume() {
		return volume;
	}

	@Override
	public float getPitch() {
		return pitch;
	}

}
