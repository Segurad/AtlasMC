package de.atlasmc.io.protocol.play;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;

@DefaultPacketID(PacketPlay.OUT_SYNCHRONIZE_PLAYER_POSITION)
public class PacketOutSynchronizePlayerPosition extends AbstractPacket implements PacketPlayOut {
	
	private double x;
	private double y;
	private double z;
	private float yaw;
	private float pitch;
	private int flags;
	private int teleportID;
	
	/**
	 * 
	 * @return the relative flags
	 * Flags: x | y | z | yaw | pitch
	 * if a flag is set the corresponding value is treated as relative and not absolute
	 */
	public int getFlags() {
		return flags;
	}
	
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public float getYaw() {
		return yaw;
	}

	public float getPitch() {
		return pitch;
	}

	public int getTeleportID() {
		return teleportID;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setZ(double z) {
		this.z = z;
	}

	public void setYaw(float yaw) {
		this.yaw = yaw;
	}

	public void setPitch(float pitch) {
		this.pitch = pitch;
	}

	public void setFlags(int flags) {
		this.flags = flags;
	}

	public void setTeleportID(int teleportID) {
		this.teleportID = teleportID;
	}

	@Override
	public int getDefaultID() {
		return OUT_SYNCHRONIZE_PLAYER_POSITION;
	}

}
