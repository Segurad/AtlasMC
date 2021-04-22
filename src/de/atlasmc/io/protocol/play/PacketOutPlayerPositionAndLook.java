package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutPlayerPositionAndLook extends Packet {
	
	public double getX();
	public double getY();
	public double getZ();
	public float getYaw();
	public float getPitch();
	
	/**
	 * 
	 * @return the relative flags
	 * Flags: x | y | z | yaw | pitch
	 * if a flag is set the corresponding value is treated as relative and not absolute
	 */
	public int getFlags();
	public int getTeleportID();
	
	@Override
	public default int getDefaultID() {
		return 0x34;
	}

}
