package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.Location;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketInPlayerRotation;
import io.netty.buffer.ByteBuf;

public class CorePacketInPlayerRotation extends AbstractPacket implements PacketInPlayerRotation {

	public CorePacketInPlayerRotation() {
		super(CoreProtocolAdapter.VERSION);
	}

	private float yaw,pitch;
	private boolean onGround;
	
	@Override
	public void read(ByteBuf in) throws IOException {
		yaw = in.readFloat();
		pitch = in.readFloat();
		onGround = in.readBoolean();
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		out.writeFloat(yaw);
		out.writeFloat(pitch);
		out.writeBoolean(onGround);
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
		loc.setPitch(pitch);
		loc.setYaw(yaw);
	}
	
	

}
