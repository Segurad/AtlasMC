package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutEntityEffect;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityEffect extends AbstractPacket implements PacketOutEntityEffect {

	private int flags, entityID, effectID, amplifier, duration;
	
	public CorePacketOutEntityEffect() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutEntityEffect(int entityID, int effectID, int amplifier, int duration, boolean isAmbient, boolean showParticles, boolean showIcon) {
		this();
		this.amplifier = amplifier;
		this.entityID = entityID;
		this.effectID = effectID;
		this.duration = duration;
		if (isAmbient) flags += 0x01;
		if (showParticles) flags += 0x02;
		if (showIcon) flags += 0x04;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		entityID = readVarInt(in);
		effectID = in.readByte();
		amplifier = in.readByte();
		duration = readVarInt(in);
		flags = in.readByte();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(entityID, out);
		out.writeByte(effectID);
		out.writeByte(amplifier);
		writeVarInt(duration, out);
		out.writeByte(flags);
	}

	@Override
	public int getEntityID() {
		return entityID;
	}

	@Override
	public int getEffectID() {
		return effectID;
	}

	@Override
	public int getAmplifier() {
		return amplifier;
	}

	@Override
	public int getDuration() {
		return duration;
	}

	@Override
	public boolean isAmbient() {
		return (flags & 0x01) == 0x01;
	}

	@Override
	public boolean getShowParticles() {
		return (flags & 0x02) == 0x02;
	}

	@Override
	public boolean getShowIcon() {
		return (flags & 0x04) == 0x04;
	}

}
