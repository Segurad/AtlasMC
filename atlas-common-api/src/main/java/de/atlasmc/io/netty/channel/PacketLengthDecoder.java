package de.atlasmc.io.netty.channel;

import static de.atlasmc.io.PacketUtil.MAX_PACKET_LENGTH;
import static de.atlasmc.io.PacketUtil.readVarInt;

import java.util.List;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * Decodes the packet size and splits the input
 */
public class PacketLengthDecoder extends ByteToMessageDecoder {

	private int length = -1;
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		Object decode = decode(ctx, in);
		if (decode != null)
			out.add(decode);
	}

	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) {
		if (length == -1) {
			length = readVarInt(in);
		}
		if (in.readableBytes() < length) 
			return null;
		if (length > MAX_PACKET_LENGTH) {
			in.skipBytes(length);
			length = -1;
			return null;
		}
		int tlength = length;
		length = -1;
		System.out.println("packet length: " + tlength);
		return in.readBytes(tlength);
	}

}
