package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutMapData;
import de.atlasmc.map.MapIcon;
import de.atlasmc.map.MapIcon.IconType;
import io.netty.buffer.ByteBuf;

public class CorePacketOutMapData extends PacketIO<PacketOutMapData> {

	@Override
	public void read(PacketOutMapData packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setMapID(readVarInt(in));
		packet.setScale(in.readByte());
		packet.setTrackingPosition(in.readBoolean());
		packet.setLocked(in.readBoolean());
		final int count = readVarInt(in);
		if (count > 0) {
			List<MapIcon> icons = new ArrayList<>(count);
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
			packet.setIcons(icons);
		}
		packet.setColums(in.readUnsignedByte());
		if (packet.getColums() <= 0) 
			return;
		packet.setRows(in.readUnsignedByte());
		packet.setOffX(in.readByte());
		packet.setOffZ(in.readByte());
		int length = readVarInt(in);
		byte[] data = new byte[length];
		in.readBytes(data);
		packet.setData(data);
	}

	@Override
	public void write(PacketOutMapData packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getMapID(), out);
		out.writeByte(packet.getScale());
		out.writeBoolean(packet.isTrackingPosition());
		out.writeBoolean(packet.isLocked());
		if (packet.getIcons() != null) {
			writeVarInt(packet.getIcons().size(), out);
			for (MapIcon i : packet.getIcons()) {
				writeVarInt(i.getType().ordinal(), out);
				out.writeByte(i.getX());
				out.writeByte(i.getZ());
				out.writeByte(i.getDirection());
				out.writeBoolean(i.hasDisplayName());
				if (i.hasDisplayName())
					writeString(i.getDisplayName(), out);
			}
		} else writeVarInt(0, out);
		out.writeByte(packet.getColums());
		if (packet.getColums() <= 0) 
			return;
		out.writeByte(packet.getRows());
		out.writeByte(packet.getOffX());
		out.writeByte(packet.getOffZ());
		writeVarInt(packet.getData().length, out);
		out.writeBytes(packet.getData());
	}

	@Override
	public PacketOutMapData createPacketData() {
		return new PacketOutMapData();
	}

}
