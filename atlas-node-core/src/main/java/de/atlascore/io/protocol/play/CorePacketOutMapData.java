package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutMapData;
import de.atlasmc.map.MapIcon;
import de.atlasmc.map.MapIcon.IconType;
import io.netty.buffer.ByteBuf;

public class CorePacketOutMapData implements PacketIO<PacketOutMapData> {

	@Override
	public void read(PacketOutMapData packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setMapID(readVarInt(in));
		packet.setScale(in.readByte());
		packet.setLocked(in.readBoolean());
		if (in.readBoolean()) {
			final int count = readVarInt(in);
			if (count > 0) {
				List<MapIcon> icons = new ArrayList<>(count);
				for (int i = 0; i < count; i++) {
					int type = readVarInt(in);
					byte x = in.readByte();
					byte z = in.readByte();
					float direction = Math.clamp(in.readByte() * 16, 0, 360);
					boolean name = in.readBoolean();
					String dname = null;
					if (name) 
						dname = readString(in);
					MapIcon icon = new MapIcon(IconType.getByID(type), x, z, direction);
					icon.setDisplayName(dname);
					icons.add(icon);
				}
				packet.setIcons(icons);
			}
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
		out.writeBoolean(packet.isLocked());
		List<MapIcon> icons = packet.getIcons();
		if (icons != null && !icons.isEmpty()) {
			out.writeBoolean(true);
			writeVarInt(packet.getIcons().size(), out);
			for (MapIcon i : packet.getIcons()) {
				writeVarInt(i.getType().ordinal(), out);
				out.writeByte(i.getX());
				out.writeByte(i.getZ());
				out.writeByte((int) (i.getDirection() / 16));
				out.writeBoolean(i.hasDisplayName());
				if (i.hasDisplayName())
					writeString(i.getDisplayName(), out);
			}
		} else {
			out.writeBoolean(false);
		}
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

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutMapData.class);
	}

}
