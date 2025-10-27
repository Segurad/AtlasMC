package de.atlasmc.core.node.io.protocol.play;

import static de.atlasmc.node.io.protocol.ProtocolUtil.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.node.entity.metadata.type.MetaData;
import de.atlasmc.node.entity.metadata.type.MetaDataField;
import de.atlasmc.node.entity.metadata.type.MetaDataType;
import de.atlasmc.node.io.protocol.play.PacketOutSetEntityMetadata;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class CorePacketOutSetEntityMetadata implements PacketIO<PacketOutSetEntityMetadata> {

	@Override
	public void read(PacketOutSetEntityMetadata packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.entityID = readVarInt(in);
		List<MetaData<?>> data = null;
		int index = 0;
		final CodecContext context = handler.getCodecContext();
		while ((index = in.readUnsignedByte()) != 0xFF) {
			if (data == null)
				data = new ArrayList<>();
			int typeID = readVarInt(in);
			MetaDataType<?> type = MetaDataType.getByID(typeID);
			Object value = type.read(in, context);
			MetaDataField<?> field = new MetaDataField<>(index, null, type);
			@SuppressWarnings({ "unchecked", "rawtypes" })
			MetaData<?> meta = new MetaData(field, value);
			data.add(meta);
		}
		packet.data = data;
	}

	@Override
	public void write(PacketOutSetEntityMetadata packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.entityID, out);
		final CodecContext context = handler.getCodecContext();
		for (MetaData<?> data : packet.data) {
			out.writeByte(data.getIndex());
			MetaDataType<?> type = data.getType();
			writeVarInt(type.getTypeID(), out);
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
