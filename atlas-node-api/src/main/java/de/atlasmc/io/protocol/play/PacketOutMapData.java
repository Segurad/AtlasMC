package de.atlasmc.io.protocol.play;

import java.util.List;

import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.DefaultPacketID;
import de.atlasmc.map.MapIcon;

@DefaultPacketID(PacketPlay.OUT_MAP_DATA)
public class PacketOutMapData extends AbstractPacket implements PacketPlayOut {
	
	private int mapID, scale, colums, rows, offX, offZ;
	private boolean locked;
	private List<MapIcon> icons;
	private byte[] data;
	
	public int getMapID() {
		return mapID;
	}

	public int getScale() {
		return scale;
	}

	public int getColums() {
		return colums;
	}

	public int getRows() {
		return rows;
	}

	public int getOffX() {
		return offX;
	}

	public int getOffZ() {
		return offZ;
	}

	public boolean isLocked() {
		return locked;
	}

	public List<MapIcon> getIcons() {
		return icons;
	}

	public byte[] getData() {
		return data;
	}

	public void setMapID(int mapID) {
		this.mapID = mapID;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public void setColums(int colums) {
		this.colums = colums;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public void setOffX(int offX) {
		this.offX = offX;
	}

	public void setOffZ(int offZ) {
		this.offZ = offZ;
	}

	public void setLocked(boolean locked) {
		this.locked = locked;
	}

	public void setIcons(List<MapIcon> icons) {
		this.icons = icons;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	@Override
	public int getDefaultID() {
		return OUT_MAP_DATA;
	}

}
