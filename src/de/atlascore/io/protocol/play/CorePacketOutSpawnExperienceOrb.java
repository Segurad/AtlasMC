package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.entity.ExperiemceOrb;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutSpawnExperienceOrb;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSpawnExperienceOrb extends AbstractPacket implements PacketOutSpawnExperienceOrb {

	private int id, count;
	private double x, y, z;
	
	public CorePacketOutSpawnExperienceOrb() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutSpawnExperienceOrb(ExperiemceOrb orb) {
		this();
		id = orb.getID();
		x = orb.getX();
		y = orb.getY();
		z = orb.getZ();
		count = orb.getExperience();
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		id = readVarInt(in);
		x = in.readDouble();
		y = in.readDouble();
		z = in.readDouble();
		count = in.readShort();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(id, out);
		out.writeDouble(x);
		out.writeDouble(y);
		out.writeDouble(z);
		out.writeShort(count);
	}

	@Override
	public int getEntityID() {
		return id;
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getY() {
		return y;
	}

	@Override
	public double getZ() {
		return z;
	}

	@Override
	public int getExperience() {
		return count;
	}

}
