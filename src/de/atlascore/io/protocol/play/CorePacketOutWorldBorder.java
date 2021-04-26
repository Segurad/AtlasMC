package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutWorldBorder;
import io.netty.buffer.ByteBuf;

public class CorePacketOutWorldBorder extends AbstractPacket implements PacketOutWorldBorder {

	private double newDiameter, oldDiameter, x, z;
	private long speed;
	private int warnTime, warnBlocks, portalBoundary, action;
	
	public CorePacketOutWorldBorder() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutWorldBorder(BorderAction action, double x, double z, double oldDiameter, double newDiameter, long speed, int portalBoundary, int warnBlocks, int warnTime) {
		this();
		this.action = action.getID();
		this.x = x;
		this.z = z;
		this.oldDiameter = oldDiameter;
		this.newDiameter = newDiameter;
		this.speed = speed;
		this.portalBoundary = portalBoundary;
		this.warnBlocks = warnBlocks;
		this.warnTime = warnTime;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		action = readVarInt(in);
		switch(action) {
		case 0:
			newDiameter = in.readDouble();
			break;
		case 1:
			oldDiameter = in.readDouble();
			newDiameter = in.readDouble();
			speed = readVarLong(in);
			break;
		case 2:
			x = in.readDouble();
			z = in.readDouble();
			break;
		case 3:
			x = in.readDouble();
			z = in.readDouble();
			oldDiameter = in.readDouble();
			newDiameter = in.readDouble();
			speed = readVarLong(in);
			portalBoundary = readVarInt(in);
			warnBlocks = readVarInt(in);
			warnTime = readVarInt(in);
			break;
		case 4:
			warnTime = readVarInt(in);
			break;
		case 5:
			warnBlocks = readVarInt(in);
			break;
		}
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(action, out);
		switch(action) {
		case 0:
			out.writeDouble(newDiameter);
			break;
		case 1:
			out.writeDouble(oldDiameter);
			out.writeDouble(newDiameter);
			writeVarLong(speed, out);
			break;
		case 2:
			out.writeDouble(x);
			out.writeDouble(z);
			break;
		case 3:
			out.writeDouble(x);
			out.writeDouble(z);
			out.writeDouble(oldDiameter);
			out.writeDouble(newDiameter);
			writeVarLong(speed, out);
			writeVarInt(portalBoundary, out);
			writeVarInt(warnBlocks, out);
			writeVarInt(warnTime, out);
			break;
		case 4:
			writeVarInt(warnTime, out);
			break;
		case 5:
			writeVarInt(warnBlocks, out);
			break;
		}
	}

	@Override
	public BorderAction getAction() {
		return BorderAction.getByID(action);
	}

	@Override
	public double getX() {
		return x;
	}

	@Override
	public double getZ() {
		return z;
	}

	@Override
	public double getNewDiameter() {
		return newDiameter;
	}

	@Override
	public double getOldDiameter() {
		return oldDiameter;
	}

	@Override
	public long getSpeed() {
		return speed;
	}

	@Override
	public int getPortalBoundary() {
		return portalBoundary;
	}

	@Override
	public int getWarnTime() {
		return warnTime;
	}

	@Override
	public int getWarnBlocks() {
		return warnBlocks;
	}

}
