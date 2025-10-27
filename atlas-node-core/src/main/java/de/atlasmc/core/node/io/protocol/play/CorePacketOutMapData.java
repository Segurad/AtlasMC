package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.chat.Chat;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.io.protocol.play.PacketOutMapData;
import de.atlasmc.node.map.MapIcon;
import de.atlasmc.node.map.MapIcon.IconType;
import de.atlasmc.util.EnumUtil;
import de.atlasmc.util.EnumUtil.EnumData;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class CorePacketOutMapData implements PacketIO<PacketOutMapData> {

	@Override
	public void read(PacketOutMapData packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.mapID = readVarInt(in);
		packet.scale = in.readByte();
		packet.locked = in.readBoolean();
		if (in.readBoolean()) {
			final int count = readVarInt(in);
			final EnumData<IconType> iconType = EnumUtil.getData(IconType.class);
			final CodecContext context = handler.getCodecContext();
			if (count > 0) {
				List<MapIcon> icons = new ArrayList<>(count);
				for (int i = 0; i < count; i++) {
					int type = readVarInt(in);
					byte x = in.readByte();
					byte z = in.readByte();
					float direction = Math.clamp(in.readByte() * 16, 0, 360);
					Chat dname = null;
					if (in.readBoolean()) 
						dname = Chat.STREAM_CODEC.deserialize(in, context);
					MapIcon icon = new MapIcon(iconType.getByID(type), x, z, direction);
					icon.setDisplayName(dname);
					icons.add(icon);
				}
				packet.icons = icons;
			}
		}
		packet.colums = in.readUnsignedByte();
		if (packet.colums <= 0) 
			return;
		packet.rows = in.readUnsignedByte();
		packet.offX = in.readByte();
		packet.offZ = in.readByte();
		int length = readVarInt(in);
		byte[] data = new byte[length];
		in.readBytes(data);
		packet.data = data;
	}

	@Override
	public void write(PacketOutMapData packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.mapID, out);
		out.writeByte(packet.scale);
		out.writeBoolean(packet.locked);
		List<MapIcon> icons = packet.icons;
		if (icons != null && !icons.isEmpty()) {
			out.writeBoolean(true);
			final int size = icons.size();
			writeVarInt(size, out);
			final CodecContext context = handler.getCodecContext();
			for (int i = 0; i < size; i++) {
				MapIcon icon = icons.get(i);
				writeVarInt(icon.getType().getID(), out);
				out.writeByte((int) icon.getX());
				out.writeByte((int) icon.getZ());
				out.writeByte((int) (icon.getRotation() / 16));
				out.writeBoolean(icon.hasDisplayName());
				if (icon.hasDisplayName())
					Chat.STREAM_CODEC.serialize(icon.getDisplayName(), out, context);
			}
		} else {
			out.writeBoolean(false);
		}
		out.writeByte(packet.colums);
		if (packet.colums <= 0) 
			return;
		out.writeByte(packet.rows);
		out.writeByte(packet.offX);
		out.writeByte(packet.offZ);
		writeVarInt(packet.data.length, out);
		out.writeBytes(packet.data);
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
