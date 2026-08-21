package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.io.PacketUtil.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.io.metadata.MetaDataInfo;
import de.atlasmc.io.metadata.MetaDataType;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.node.io.protocol.play.PacketOutSetEntityMetadata;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetEntityMetadata implements PacketCodec<PacketOutSetEntityMetadata> {

	@Override
	public void deserialize(PacketOutSetEntityMetadata packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.entityID = readVarInt(in);
		List<MetaDataInfo<Object>> data = null;
		int index = 0;
		final CodecContext context = handler.getCodecContext();
		while ((index = in.readUnsignedByte()) != 0xFF) {
			if (data == null)
				data = new ArrayList<>();
			int typeID = readVarInt(in);
			@SuppressWarnings("unchecked")
			MetaDataType<Object> type = (MetaDataType<Object>) EntityMetaTypes.getByID(typeID);
			Object value = type.read(in, context);
			var meta = new MetaDataInfo<>(index, type, value);
			data.add(meta);
		}
		packet.data = data;
	}

	@Override
	public void serialize(PacketOutSetEntityMetadata packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.entityID, out);
		final CodecContext context = handler.getCodecContext();
		for (var data : packet.data) {
			out.writeByte(data.getIndex());
			MetaDataType<Object> type = data.getType();
			writeVarInt(type.getID(), out);
			type.writeRaw(data.getData(), out, context);
		}
		out.writeByte(0xFF);
	}

	@Override
	public PacketOutSetEntityMetadata createPacketData() {
		return new PacketOutSetEntityMetadata();
	}

	@Override
	public int getPacketID() {
		return Packet.getDefaultPacketID(PacketOutSetEntityMetadata.class);
	}
	
}
