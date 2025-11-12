package de.atlasmc.node.entity.metadata.type;

import java.util.UUID;

import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class OptUUIDMetaType extends MetaDataType<UUID> {

	public OptUUIDMetaType(int type) {
		super(type, UUID.class, true);
	}

    @Override
    public UUID read(ByteBuf in, CodecContext context) {
        if (!in.readBoolean()) 
        	return null;
        long most = in.readLong();
        long least = in.readLong();
        return new UUID(most, least);
    }

    @Override
    public void write(UUID data, ByteBuf out, CodecContext context) {
        out.writeBoolean(data != null);
        if (data == null) return;
        out.writeLong(data.getMostSignificantBits());
        out.writeLong(data.getLeastSignificantBits());
    }

}
