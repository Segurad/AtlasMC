package de.atlasmc.io.netty.channel;

import static de.atlasmc.io.PacketUtil.MAX_PACKET_LENGTH;
import static de.atlasmc.io.PacketUtil.readVarInt;

import java.util.List;

import de.atlasmc.io.PacketUtil;
import de.atlasmc.io.ProtocolException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

/**
 * Decodes the packet size and splits the input
 */
public class PacketLengthDecoder extends ByteToMessageDecoder {

	private final ByteBuf lengthBuf = Unpooled.buffer(3);
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		in.markReaderIndex();
		int length = -1;
		if (checkLength(in)) {
			length = readVarInt(lengthBuf);
		}
		if (length == -1 || in.readableBytes() < length) {
			in.resetReaderIndex();
			return;
		}
		if (length > MAX_PACKET_LENGTH) {
			throw new ProtocolException("Packet too large!");
		}
		out.add(in.readBytes(length));
	}
	
	private boolean checkLength(ByteBuf in) {
		lengthBuf.clear();
		for (int i = 0; i < 3; i++) {
			if (!in.isReadable())
				return false;
			byte b = in.readByte();
			lengthBuf.writeByte(b);
			if ((b & PacketUtil.VAR_CONTINUE_BIT) == 0)
				return true;
		}
		throw new ProtocolException("Invalid packet length!");
	}

}
