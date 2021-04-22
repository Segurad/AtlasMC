package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.Particle;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutParticle;
import io.netty.buffer.ByteBuf;

public class CorePacketOutParticle extends AbstractPacket implements PacketOutParticle {

	private int id, count;
	private double x, y, z;
	private float offX, offY, offZ, particleData;
	private byte[] data;
	private boolean longDistance;
	
	public CorePacketOutParticle() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutParticle(Particle particle, boolean longDistance, double x, double y, double z, float offX, float offY, float offZ, float particleData, int count, byte[] data) {
		this();
		this.id = particle.ordinal();
		this.longDistance = longDistance;
		this.x = x;
		this.y = y;
		this.z = z;
		this.offX = offX;
		this.offY = offY;
		this.offZ = offZ;
		this.particleData = particleData;
		this.count = count;
		this.data = data;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		id = in.readInt();
		longDistance = in.readBoolean();
		x = in.readDouble();
		y = in.readDouble();
		z = in.readDouble();
		offX = in.readFloat();
		offY = in.readFloat();
		offZ = in.readFloat();
		particleData = in.readFloat();
		count = in.readInt();
		data = new byte[in.readableBytes()];
		in.readBytes(data);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeInt(id);
		out.writeBoolean(longDistance);
		out.writeDouble(x);
		out.writeDouble(y);
		out.writeDouble(z);
		out.writeFloat(offX);
		out.writeFloat(offY);
		out.writeFloat(offZ);
		out.writeFloat(particleData);
		out.writeInt(count);
		out.writeBytes(data);
	}

	@Override
	public Particle getParticle() {
		return Particle.getByID(id);
	}

	@Override
	public boolean isLongDistance() {
		return longDistance;
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
	public float getOffsetX() {
		return offX;
	}

	@Override
	public float getOffsetY() {
		return offY;
	}

	@Override
	public float getOffsetZ() {
		return offZ;
	}

	@Override
	public float getParticleData() {
		return particleData;
	}

	@Override
	public int getParticleCount() {
		return count;
	}

	@Override
	public byte[] getData() {
		return data;
	}

}
