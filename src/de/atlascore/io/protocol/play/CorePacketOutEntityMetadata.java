package de.atlascore.io.protocol.play;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.atlascore.io.ConnectionHandler;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import static de.atlasmc.io.AbstractPacket.*;

import de.atlasmc.io.PacketIO;
import de.atlasmc.io.protocol.play.PacketOutEntityMetadata;
import io.netty.buffer.ByteBuf;

public class CorePacketOutEntityMetadata extends PacketIO<PacketOutEntityMetadata> {

	@Override
	public void read(PacketOutEntityMetadata packet, ByteBuf in, ConnectionHandler handler) throws IOException {
		packet.setEntityID(readVarInt(in));
		List<MetaData<?>> data = null;
		int index = 0;
		while ((index = in.readUnsignedByte()) != 0xFF) {
			if (data == null)
				data = new ArrayList<>();
			int typeID = readVarInt(in);
			MetaDataType<?> type = MetaDataType.getByID(typeID);
			Object value = type.read(in);
			MetaDataField<?> field = new MetaDataField<>(index, null, type);
			@SuppressWarnings({ "unchecked", "rawtypes" })
			MetaData<?> meta = new MetaData(field, value);
			data.add(meta);
		}
		packet.setData(data);
	}

	@Override
	public void write(PacketOutEntityMetadata packet, ByteBuf out, ConnectionHandler handler) throws IOException {
		writeVarInt(packet.getEntityID(), out);
		for (MetaData<?> data : packet.getData()) {
			out.writeByte(data.getIndex());
			MetaDataType<?> type = data.getType();
			writeVarInt(type.getTypeID(), out);
			type.writeRaw(data.getData(), out);
		}
		out.writeByte(0xFF);
	}

	@Override
	public PacketOutEntityMetadata createPacketData() {
		return new PacketOutEntityMetadata();
	}
	
}
