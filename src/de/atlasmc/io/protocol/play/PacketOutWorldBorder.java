package de.atlasmc.io.protocol.play;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;

@DefaultPacketID(PacketPlay.OUT_WORLD_BORDER)
public interface PacketOutWorldBorder extends PacketPlay, PacketOutbound {
	
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
		return OUT_WORLD_BORDER;
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
