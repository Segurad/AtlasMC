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
	
	/**
	 * Reads a UTF-8 string prefixed with its byte length.
	 * The max length is the max number of chars (UTF-16 code units) like in the minecraft protocol.
	 * @param in
	 * @param maxLength max number of chars
	 * @return string or null if the length is 0
	 */
	public static String readString(ByteBuf in, int maxLength) {
		int len = PacketUtil.readVarInt(in);
		if (len == 0) 
			return null;
		if (len < 0 || len > maxLength * 3) 
			throw new CodecException("Invalid String byte length: " + len + " expected max: " + (maxLength * 3));
		if (len > in.readableBytes())
			throw new CodecException("String byte length " + len + " exceeds readable bytes: " + in.readableBytes());
		String value = in.readString(len, StandardCharsets.UTF_8);
		if (value.length() > maxLength)
			throw new CodecException("String was longer than max length " + maxLength + ": " + value.length());
		return value;
	}
	
	public static void writeString(String value, ByteBuf out) {
		writeString(value, out, MAX_LENGTH);
	}
	
	public static void writeString(String value, ByteBuf out, int maxLength) {
		if (value == null)  {
			PacketUtil.writeVarInt(0, out);
			return;
		}
		if (value.length() > maxLength)
			throw new CodecException("String was longer than max length " + maxLength + ": " + value.length());
		PacketUtil.writeVarInt(ByteBufUtil.utf8Bytes(value), out);
		ByteBufUtil.writeUtf8(out, value);
	}
	
}
