package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutEntityEffect;
import de.atlasmc.potion.PotionEffect;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityEffect extends AbstractPacket implements PacketOutEntityEffect {

	protected static final int
	FLAG_IS_AMBIENT = 0x01,
	FLAG_SHOW_PARTICLES = 0x02,
	FLAG_SHOW_ICON = 0x04;
	
	private int flags;
	private int entityID;
	private int effectID;
	private int amplifier;
	private int duration;
	
	public CorePacketOutEntityEffect() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutEntityEffect(int entityID, int effectID, int amplifier, int duration, boolean isAmbient, boolean showParticles, boolean showIcon) {
		this();
		this.amplifier = amplifier;
		this.entityID = entityID;
		this.effectID = effectID;
		this.duration = duration;
		if (isAmbient) 
			flags |= FLAG_IS_AMBIENT;
		if (showParticles) 
			flags |= FLAG_SHOW_PARTICLES;
		if (showIcon) 
			flags |= FLAG_SHOW_ICON;
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

	@Override
	public void setEntityID(int id) {
		this.entityID = id;
	}

	@Override
	public void setEffect(PotionEffect effect) {
		effectID = effect.getType().getID();
		amplifier = effect.getAmplifier();
		duration = effect.getDuration();
		flags = 0;
		if (effect.hasReducedAmbient())
			flags |= FLAG_IS_AMBIENT;
		if (effect.hasParticels())
			flags |= FLAG_SHOW_PARTICLES;
		if (effect.isShowingIcon())
			flags |= FLAG_SHOW_ICON;
	}

}
