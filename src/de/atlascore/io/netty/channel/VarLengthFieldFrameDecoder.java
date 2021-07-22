package de.atlascore.io.netty.channel;

import java.util.List;

import de.atlasmc.io.AbstractPacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class VarLengthFieldFrameDecoder extends ByteToMessageDecoder {

	private int length = -1;
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		Object decode = decode(ctx, in);
		if (decode != null)
			out.add(decode);
	}

	protected Object decode(ChannelHandlerContext ctx, ByteBuf in) {
		if (length == -1) {
			length = AbstractPacket.readVarInt(in);
		}
		if (in.readableBytes() < length) return null;
		if (length > AbstractPacket.MAX_PACKET_LENGTH) {
			in.skipBytes(length);
			length = -1;
			return null;
		}
		int tlength = length;
		length = -1;
		System.out.println(tlength);
		return in.readBytes(tlength);
	}

}
