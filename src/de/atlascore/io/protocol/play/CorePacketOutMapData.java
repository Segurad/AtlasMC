package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutMapData;
import de.atlasmc.map.MapIcon;
import de.atlasmc.map.MapIcon.IconType;
import io.netty.buffer.ByteBuf;

public class CorePacketOutMapData extends AbstractPacket implements PacketOutMapData {

	private int id, scale, colums, rows, offX, offZ;
	private boolean trackingPosition, locked;
	private List<MapIcon> icons;
	private byte[] data;
	
	public CorePacketOutMapData() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutMapData(int id, int scale, boolean trackingPosition, boolean locked, List<MapIcon> icons) {
		this(id, scale, trackingPosition, locked, icons, 0, 0, 0, 0, null);
	}
	
	public CorePacketOutMapData(int id, int scale, boolean trackingPosition, boolean locked, List<MapIcon> icons, int colums, int rows, int offX, int offZ, byte[] data) {
		this();
		this.id = id;
		this.scale = scale;
		this.trackingPosition = trackingPosition;
		this.locked = locked;
		this.icons = new ArrayList<MapIcon>(icons.size());
		for (MapIcon i : icons) {
			this.icons.add(i.clone());
		}
		this.colums = colums;
		this.rows = rows;
		this.offX = offX;
		this.offZ = offZ;
		this.data = data;
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		id = readVarInt(in);
		scale = in.readByte();
		trackingPosition = in.readBoolean();
		locked = in.readBoolean();
		final int count = readVarInt(in);
		if (count > 0) {
			icons = new ArrayList<MapIcon>(count);
			for (int i = 0; i < count; i++) {
				int type = readVarInt(in);
				byte x = in.readByte();
				byte z = in.readByte();
				byte direction = in.readByte();
				boolean name = in.readBoolean();
				String dname = null;
				if (name) dname = readString(in);
				MapIcon icon = new MapIcon(IconType.getByID(type), x, z, direction);
				icon.setDisplayName(dname);
				icons.add(icon);
			}
		}
		colums = in.readUnsignedByte();
		if (colums <= 0) return;
		rows = in.readUnsignedByte();
		offX = in.readByte();
		offZ = in.readByte();
		int length = readVarInt(in);
		data = new byte[length];
		in.readBytes(data);
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(id, out);
		out.writeByte(scale);
		out.writeBoolean(trackingPosition);
		out.writeBoolean(locked);
		if (icons != null) {
			writeVarInt(icons.size(), out);
			for (MapIcon i : icons) {
				writeVarInt(i.getType().ordinal(), out);
				out.writeByte(i.getX());
				out.writeByte(i.getZ());
				out.writeByte(i.getDirection());
				out.writeBoolean(i.hasDisplayName());
				if (i.hasDisplayName())
					writeString(i.getDisplayName(), out);
			}
		} else writeVarInt(0, out);
		out.writeByte(colums);
		if (colums <= 0) return;
		out.writeByte(rows);
		out.writeByte(offX);
		out.writeByte(offZ);
		writeVarInt(data.length, out);
		out.writeBytes(data);
	}

	public int getMapID() {
		return id;
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

	public boolean isTrackingPosition() {
		return trackingPosition;
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

}
