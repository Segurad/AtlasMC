package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutFacePlayer;
import io.netty.buffer.ByteBuf;

public class CorePacketOutFacePlayer extends AbstractPacket implements PacketOutFacePlayer {

	private boolean aimWithEyes, aimAtEyes, isEntity;
	private double x, y, z;
	private int entityID;
	
	public CorePacketOutFacePlayer() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutFacePlayer(boolean aimWithEyes, double targetX, double targetY, double targetZ, boolean isEntity, int entityID, boolean aimAtEyes) {
		this();
		this.aimWithEyes = aimWithEyes;
		this.x = targetX;
		this.y = targetY;
		this.z = targetZ;
		this.isEntity = isEntity;
		this.entityID = entityID;
		this.aimAtEyes = aimAtEyes;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		aimWithEyes = readVarInt(in) == 1;
		x = in.readDouble();
		y = in.readDouble();
		z = in.readDouble();
		isEntity = in.readBoolean();
		if (!isEntity) return;
		entityID = readVarInt(in);
		aimAtEyes = readVarInt(in) == 1;
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(aimWithEyes ? 1 : 0, out);
		out.writeDouble(x);
		out.writeDouble(y);
		out.writeDouble(z);
		out.writeBoolean(isEntity);
		if (!isEntity) return;
		writeVarInt(entityID, out);
		writeVarInt(aimAtEyes ? 1 : 0, out);
	}

	@Override
	public boolean getAimWithEyes() {
		return aimWithEyes;
	}

	@Override
	public double getTargetX() {
		return x;
	}

	@Override
	public double getTargetY() {
		return y;
	}

	@Override
	public double getTargetZ() {
		return z;
	}

	@Override
	public boolean isEntity() {
		return isEntity;
	}

	@Override
	public int getEntityID() {
		return entityID;
	}

	@Override
	public boolean getAimAtEyes() {
		return aimAtEyes;
	}

}
