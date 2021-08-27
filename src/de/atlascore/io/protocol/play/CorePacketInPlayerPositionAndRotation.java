package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.Location;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInPlayerPositionAndRotation;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerPositionAndRotation extends AbstractPacket implements PacketInPlayerPositionAndRotation {

	public CorePacketInPlayerPositionAndRotation() {
		super(CoreProtocolAdapter.VERSION);	
	}

	private double x,feetY,z;
	private float yaw,pitch;
	private boolean onGround;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		x = in.readDouble();
		feetY = in.readDouble();
		z = in.readDouble();
		yaw = in.readFloat();
		pitch = in.readFloat();
		onGround = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeDouble(x);
		out.writeDouble(feetY);
		out.writeDouble(z);
		out.writeFloat(yaw);
		out.writeFloat(pitch);
		out.writeBoolean(onGround);
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getFeetY() {
		return feetY;
	}

	@Override
	public double getZ() {
		return z;
	}

	@Override
	public float getYaw() {
		return yaw;
	}

	@Override
	public float getPitch() {
		return pitch;
	}

	@Override
	public boolean isOnGround() {
		return onGround;
	}

	@Override
	public void getLocation(Location loc) {
		loc.setLocation(x, feetY, z, yaw, pitch);
	}
	
	

}
