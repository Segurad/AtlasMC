package de.atlasmc.io.netty.channel;

import static de.atlasmc.io.PacketUtil.readVarInt;

import java.nio.ByteBuffer;
import java.util.List;
import java.util.zip.Inflater;

import de.atlasmc.io.PacketUtil;
import de.atlasmc.io.ProtocolException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class PacketDecompressor extends MessageToMessageDecoder<ByteBuf> {

	private final Inflater inf;
	private final int threshold;

	public PacketDecompressor(int threshold) {
		this.inf = new Inflater();
		this.threshold = threshold;
	}

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		int uncompressedDataSize = readVarInt(msg);
		if (uncompressedDataSize == 0) {
			out.add(msg.retain()); // MessageToMessageDecoder releases msg after decode()
			return;
		}
		if (uncompressedDataSize < threshold)
			throw new ProtocolException("Packet of size " + uncompressedDataSize + " was below threshold " + threshold);
		if (uncompressedDataSize > PacketUtil.MAX_UNCOMPRESSED_LENGTH)
			throw new ProtocolException("Packet of size " + uncompressedDataSize + " was above max uncompressed size " + PacketUtil.MAX_UNCOMPRESSED_LENGTH);

		if (msg.nioBufferCount() == 1) {
			inf.setInput(msg.nioBuffer());
		} else {
			inf.setInput(ByteBufUtil.getBytes(msg));
		}

		ByteBuf inflated = ctx.alloc().heapBuffer(uncompressedDataSize, uncompressedDataSize);
		try {
			ByteBuffer dst = inflated.nioBuffer(0, uncompressedDataSize);
			while (dst.hasRemaining() && !inf.finished()) {
				if (inf.inflate(dst) == 0) {
					if (inf.needsInput())
						throw new ProtocolException("Truncated compressed packet, expected " + uncompressedDataSize + " bytes but got " + dst.position());
					if (inf.needsDictionary())
						throw new ProtocolException("Compressed packet requires preset dictionary");
				}
			}
			if (!inf.finished())
				throw new ProtocolException("Compressed packet inflates to more than " + uncompressedDataSize + " bytes");
			if (dst.position() != uncompressedDataSize)
				throw new ProtocolException("Packet inflated to " + dst.position() + " bytes but declared " + uncompressedDataSize);
			inflated.writerIndex(uncompressedDataSize);
			msg.skipBytes(msg.readableBytes());
			out.add(inflated);
			inflated = null;
		} finally {
			if (inflated != null)
				inflated.release();
			inf.reset();
		}
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		inf.end();
		super.handlerRemoved(ctx);
	}

}
