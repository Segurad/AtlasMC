package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.io.PacketOutbound;
import de.atlasmc.map.MapIcon;

@DefaultPacketID(PacketPlay.OUT_MAP_DATA)
public interface PacketOutMapData extends PacketPlay, PacketOutbound {
	
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
		return OUT_MAP_DATA;
	}

}
