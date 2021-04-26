package de.atlasmc.io.protocol.play;

import de.atlasmc.io.Packet;

public interface PacketOutWorldBorder extends Packet {
	
	public BorderAction getAction();
	public double getX();
	public double getZ();
	public double getNewDiameter();
	public double getOldDiameter();
	public long getSpeed();
	public int getPortalBoundary();
	public int getWarnTime();
	public int getWarnBlocks();
	
	@Override
	default int getDefaultID() {
		return 0x3D;
	}

	public static enum BorderAction {
		SET_SIZE,
		LERP_SIZE,
		SET_CENTER,
		INITIALIZE,
		SET_WARNING_TIME,
		SET_WARNING_BLOCKS;
		
		public int getID() {
			return ordinal();
		}
		
		public static BorderAction getByID(int id) {
			return values()[id];
		}
	}
	
}
