package de.atlasmc.io.netty.channel;

import java.nio.ByteBuffer;
import java.util.zip.Deflater;

import de.atlasmc.io.PacketUtil;
import de.atlasmc.io.ProtocolException;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketCompressor extends MessageToByteEncoder<ByteBuf> {

	private final Deflater def;
	private final int threshold;

	public PacketCompressor(int threshold) {
		this.threshold = threshold;
		this.def = new Deflater();
	}

	@Override
	protected ByteBuf allocateBuffer(ChannelHandlerContext ctx, ByteBuf msg, boolean preferDirect) {
		int bytes = msg.readableBytes();
		int size = 5 + (bytes < threshold ? bytes : maxCompressedLength(bytes));
		return preferDirect ? ctx.alloc().ioBuffer(size) : ctx.alloc().heapBuffer(size);
	}

	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
		int bytes = msg.readableBytes();
		if (bytes < threshold) {
			PacketUtil.writeVarInt(0, out);
			out.writeBytes(msg);
			return;
		}
		if (bytes > PacketUtil.MAX_UNCOMPRESSED_LENGTH)
			throw new ProtocolException("Uncompressed Packet of size " + bytes + " was larger max uncompressed size " + PacketUtil.MAX_UNCOMPRESSED_LENGTH);
		PacketUtil.writeVarInt(bytes, out);
		if (msg.nioBufferCount() == 1) {
			def.setInput(msg.nioBuffer());
		} else {
			def.setInput(ByteBufUtil.getBytes(msg));
		}
		def.finish();
		try {
			while (!def.finished()) {
				int writable = out.writableBytes();
				if (writable == 0)
					throw new IllegalStateException("Compression output buffer too small");
				ByteBuffer dst = out.nioBuffer(out.writerIndex(), writable);
				int n = def.deflate(dst);
				out.writerIndex(out.writerIndex() + n);
			}
		} finally {
			def.reset();
		}
		msg.skipBytes(bytes);
	}

	@Override
	public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
		def.end();
		super.handlerRemoved(ctx);
	}

	// zlib compressBound()
	private static int maxCompressedLength(int n) {
		return n + (n >>> 12) + (n >>> 14) + (n >>> 25) + 13;
	}

}
