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
		if (container == null || !container.hasChanges()) {
			buf = Unpooled.buffer(1);
			buf.writeByte(0xFF);
		}
		buf = Unpooled.buffer();
		try {
			for (MetaData<? extends Object> data : container.getValues()) {
				if (!data.hasChanged()) continue;
				buf.writeByte(data.getIndex());
				MetaDataType<?> type = data.getType();
				writeVarInt(type.getType(), buf);
				type.write(data.getData(), buf);
			}
		} catch (IOException e) {
			e.printStackTrace();
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

}
