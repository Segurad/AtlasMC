package de.atlascore.io.protocol.play;

import java.io.IOException;

import de.atlascore.io.protocol.CoreProtocolAdapter;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataContainer;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.io.AbstractPacket;
import de.atlasmc.io.protocol.play.PacketOutEntityMetadata;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class CorePacketOutEntityMetadata extends AbstractPacket implements PacketOutEntityMetadata {

	private int entityID;
	private ByteBuf buf;
	
	public CorePacketOutEntityMetadata() {
		super(CoreProtocolAdapter.VERSION);
	}
	
	public CorePacketOutEntityMetadata(int entityID, MetaDataContainer container) {
		this();
		this.entityID = entityID;
		if (container == null || !container.hasChanges())
			return;
		buf = Unpooled.buffer();
		for (MetaData<? extends Object> data : container) {
			if (!data.hasChanged()) continue;
			buf.writeByte(data.getIndex());
			MetaDataType<?> type = data.getType();
			writeVarInt(type.getTypeID(), buf);
			type.writeRaw(data.getData(), buf);
		}
		buf.writeByte(0xFF);
	}

	@Override
	public void read(ByteBuf in) throws IOException {
		entityID = readVarInt(in);
		buf = in.readBytes(in.readableBytes());
	}

	@Override
	public void write(ByteBuf out) throws IOException {
		writeVarInt(entityID, out);
		if (buf == null)
			out.writeByte(0xFF);
		else
			out.writeBytes(buf);
	}

	@Override
	public int getEntityID() {
		return entityID;
	}

	@Override
	public ByteBuf getData() {
		return buf;
	}

	@Override
	public void setEntityID(int id) {
		this.entityID = id;	
	}

	@Override
	public void setNonDefaultData(MetaDataContainer container) {
		for (MetaData<?> data : container) {
			if (data.isDefault())
				continue;
			if (buf == null)
				buf = Unpooled.buffer();
			buf.writeByte(data.getIndex());
			MetaDataType<?> type = data.getType();
			writeVarInt(type.getTypeID(), buf);
			type.writeRaw(data.getData(), buf);
		}
		buf.writeByte(0xFF);
	}

	@Override
	public void setChangedData(MetaDataContainer container) {
		if (!container.hasChanges())
			return;
		for (MetaData<?> data : container) {
			if (!data.hasChanged())
				continue;
			if (buf == null)
				buf = Unpooled.buffer();
			buf.writeByte(data.getIndex());
			MetaDataType<?> type = data.getType();
			writeVarInt(type.getTypeID(), buf);
			type.writeRaw(data.getData(), buf);
		}
		buf.writeByte(0xFF);
	}

}
