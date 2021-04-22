package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutExplosion;
import io.netty.buffer.ByteBuf;

public class CorePacketOutExplosion extends AbstractPacket implements PacketOutExplosion {

	private float x, y, z, strength, mx, my, mz;
	private byte[] records;
	
	public CorePacketOutExplosion() {
		super(CoreProtocolAdapter.VERSION);	
	}
	
	public CorePacketOutExplosion(float x, float y, float z, float strength, byte[] records, float motionX, float motionY, float motionZ) {
		this();
		this.x = x;
		this.y = y;
		this.z = z;
		this.strength = strength;
		this.records = records;
		this.mx = motionX;
		this.my = motionY;
		this.mz = motionZ;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		x = in.readFloat();
		y = in.readFloat();
		z = in.readFloat();
		strength = in.readFloat();
		final int count = in.readInt()*3;
		records = new byte[count];
		in.readBytes(records);
		mx = in.readFloat();
		my = in.readFloat();
		mz = in.readFloat();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeFloat(x);
		out.writeFloat(y);
		out.writeFloat(z);
		out.writeFloat(strength);
		out.writeInt(records.length/3);
		out.writeBytes(records);
		out.writeFloat(mx);
		out.writeFloat(my);
		out.writeFloat(mz);
	}

	@Override
	public float getX() {
		return x;
	}

	@Override
	public float getY() {
		return y;
	}

	@Override
	public float getZ() {
		return z;
	}

	@Override
	public float getStrength() {
		return strength;
	}

	@Override
	public float getMotionX() {
		return mx;
	}

	@Override
	public float getMotionY() {
		return my;
	}

	@Override
	public float getMotionZ() {
		return mz;
	}

	@Override
	public byte[] getRecords() {
		return records;
	}

}
