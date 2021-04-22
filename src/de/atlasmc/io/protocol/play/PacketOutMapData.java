package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.io.Packet;
import de.atlasmc.map.MapIcon;

public interface PacketOutMapData extends Packet {
	
	public int getMapID();
	public int getScale();
	public boolean isTrackingPosition();
	public boolean isLocked();
	public List<MapIcon> getIcons();
	public int getColums();
	public int getRows();
	public int getOffX();
	public int getOffZ();
	public byte[] getData();
	
	@Override
	default int getDefaultID() {
		return 0x25;
	}

}
