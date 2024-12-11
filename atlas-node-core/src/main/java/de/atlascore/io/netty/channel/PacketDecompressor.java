package de.atlascore.io.netty.channel;

import static de.atlasmc.io.PacketUtil.readVarInt;

import java.util.List;
import java.util.zip.Inflater;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class PacketDecompressor extends MessageToMessageDecoder<ByteBuf> {

	private final Inflater inf;
	
	public PacketDecompressor() {
		this.inf = new Inflater();
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		int uncompressedDataSize = readVarInt(msg);
		if (uncompressedDataSize == 0) {
			out.add(msg);
			return;
		}
		ByteBuf inflatedBuf = ctx.alloc().ioBuffer(uncompressedDataSize);
		inf.setInput(msg.array(), msg.arrayOffset(), msg.readableBytes());
		inf.inflate(inflatedBuf.array());
		inflatedBuf.writerIndex(uncompressedDataSize);
		out.add(inflatedBuf);
		inf.reset();
	}

}
