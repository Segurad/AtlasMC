package de.atlasmc.io.codec;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;

public class UUIDCodec implements StreamCodec<UUID> {

	public static final StreamCodec<UUID> STREAM_CODEC = new UUIDCodec();
	
	private UUIDCodec() {
		// unused
	}
	
	@Override
	public Class<?> getType() {
		return UUID.class;
	}

	@Override
	public boolean serialize(UUID value, ByteBuf output, CodecContext context) throws IOException {
		output.writeLong(value.getMostSignificantBits());
		output.writeLong(value.getLeastSignificantBits());
		return true;
	}

	@Override
	public UUID deserialize(UUID value, ByteBuf input, CodecContext context) throws IOException {
		long most = input.readLong();
		long least = input.readLong();
		return new UUID(most, least);
	}
	
	@NotNull
	public static UUID readUUID(ByteBuf in) {
		long most = in.readLong();
		long least = in.readLong();
		return new UUID(most, least);
	}
	
	public static void writeUUID(UUID uuid, ByteBuf out) {
		out.writeLong(uuid.getMostSignificantBits());
		out.writeLong(uuid.getLeastSignificantBits());
	}

}
