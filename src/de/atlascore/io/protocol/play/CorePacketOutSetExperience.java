package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutSetExperiance;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetExperience extends AbstractPacket implements PacketOutSetExperiance {

	private float bar;
	private int level, experience;
	
	public CorePacketOutSetExperience() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutSetExperience(float bar, int level, int exeperience) {
		this();
		this.bar = bar;
		this.experience = exeperience;
		this.level = level;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		bar = in.readFloat();
		level = readVarInt(in);
		experience = readVarInt(in);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeFloat(bar);
		writeVarInt(level, out);
		writeVarInt(experience, out);
	}

	@Override
	public float getExperienceBar() {
		return bar;
	}

	@Override
	public int getLevel() {
		return level;
	}

	@Override
	public int getTotalExperience() {
		return experience;
	}

	@Override
	public void setLevel(int level) {
		this.level = level;
	}

	@Override
	public void setTotalExperience(int total) {
		this.experience = total;
	}

	@Override
	public void setExperienceBar(float bar) {
		this.bar = bar;
	}

}
