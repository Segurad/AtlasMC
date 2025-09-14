package de.atlasmc.node.io.protocol.play;

import java.util.List;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.node.map.MapIcon;

@DefaultPacketID(packetID = PacketPlay.OUT_MAP_DATA, definition = "map_item_data")
public class PacketOutMapData extends AbstractPacket implements PacketPlayOut {
	
	public int mapID;
	public int scale;
	public boolean locked;
	public List<MapIcon> icons;
	public int colums;
	public int rows;
	public int offX;
	public int offZ;
	public byte[] data;

	@Override
	public int getDefaultID() {
		return OUT_MAP_DATA;
	}

}
