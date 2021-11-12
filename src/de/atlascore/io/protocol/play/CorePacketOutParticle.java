package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.Particle;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutParticle;
import io.netty.buffer.ByteBuf;

public class CorePacketOutParticle extends AbstractPacket implements PacketOutParticle {

	private int id, count;
	private double x, y, z;
	private float offX, offY, offZ, particleData;
	private Object data;
	private boolean longDistance;
	
	public CorePacketOutParticle() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutParticle(Particle particle, boolean longDistance, double x, double y, double z, float offX, float offY, float offZ, float particleData, int count, Object data) {
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
		data = MetaDataType.PARTICLE.read(Particle.getByID(id), in);
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
		MetaDataType.PARTICLE.write(Particle.getByID(id), data, false, out);
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
	public Object getData() {
		return data;
	}

	@Override
	public void setLongDistance(boolean longdistance) {
		this.longDistance = longdistance;
	}

	@Override
	public void setX(double x) {
		this.x = x;
	}

	@Override
	public void setY(double y) {
		this.y = y;
	}

	@Override
	public void setZ(double z) {
		this.z = z;
	}

	@Override
	public void setPoition(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void setOffsetX(float x) {
		this.offX = x;
	}

	@Override
	public void setOffsetY(float y) {
		this.offY = y;
	}

	@Override
	public void setOffsetZ(float z) {
		this.offZ = z;
	}

	@Override
	public void setOffset(float x, float y, float z) {
		this.offX = x;
		this.offY = y;
		this.offZ = z;
	}

	@Override
	public void setParticleData(float data) {
		this.particleData = data;
	}

	@Override
	public void setParticleCount(int count) {
		this.count = count;
	}

	@Override
	public void setData(Object data) {
		this.data = data;
	}

	@Override
	public void setParticle(Particle particle) {
		id = particle.getID();
	}

}
