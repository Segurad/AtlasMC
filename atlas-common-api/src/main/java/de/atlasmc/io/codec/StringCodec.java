package de.atlasmc.io.codec;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

import de.atlasmc.io.PacketUtil;
import de.atlasmc.util.codec.CodecContext;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.handler.codec.CodecException;

public class StringCodec implements StreamCodec<String> {
	
	public static final int MAX_LENGTH = 32767;
	
	public static final StreamCodec<String> MAX_LENGTH_CODEC = new StringCodec(MAX_LENGTH);
	
	private int maxLength;
	
	public StringCodec(int maxLength) {
		this.maxLength = maxLength;
		if (maxLength < 0 || maxLength > MAX_LENGTH)
			throw new IllegalArgumentException("Value must be between 0 and " + MAX_LENGTH + ": " + maxLength);
	}

	@Override
	public Class<?> getType() {
		return String.class;
	}

	@Override
	public boolean serialize(String value, ByteBuf output, CodecContext context) throws IOException {
		writeString(value, output, maxLength);
		return true;
	}

	@Override
	public String deserialize(String value, ByteBuf input, CodecContext context) throws IOException {
		return readString(input, maxLength);
	}
	
	public static String readString(ByteBuf in) {
		return readString(in, MAX_LENGTH);
	}
	
	public static String readString(ByteBuf in, int maxLength) {
		int len = PacketUtil.readVarInt(in);
		if (len == 0) 
			return null;
		if (len > maxLength) 
			throw new CodecException("Invalid String length:" + len + " expected: " + maxLength);
		return in.readString(len, StandardCharsets.UTF_8);
	}
	
	public static void writeString(String value, ByteBuf out) {
		writeString(value, out, MAX_LENGTH);
	}
	
	public static void writeString(String value, ByteBuf out, int maxLength) {
		if (value == null)  {
			PacketUtil.writeVarInt(0, out);
			return;
		}
		int maxBytes = ByteBufUtil.utf8MaxBytes(value);
		ByteBuf buf = out.alloc().buffer(maxBytes);
		int bytes = ByteBufUtil.writeUtf8(buf, value);
		if (bytes > maxLength)
			throw new CodecException("String was longer than max length " + maxLength + ": " + bytes);
		PacketUtil.writeVarInt(bytes, out);
		out.writeBytes(buf);
	}
	
}
