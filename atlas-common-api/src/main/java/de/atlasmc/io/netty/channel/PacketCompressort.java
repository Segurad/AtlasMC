package de.atlasmc.io.netty.channel;

import java.util.zip.Deflater;

import de.atlasmc.io.PacketUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketCompressort extends MessageToByteEncoder<ByteBuf> {

	private final Deflater def;
	private final byte[] buf;
	private final int threshold;
	
	public PacketCompressort(int threshold) {
		this(threshold, 8192);
	}
	
	public PacketCompressort(int threshold, int bufsize) {
		this.threshold = threshold;
		this.def = new Deflater();
		this.buf = new byte[8192];
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
		int bytes = msg.readableBytes();
		if (bytes < threshold) { // if data is less than the threshold there is no need in compressing it
			PacketUtil.writeVarInt(0, out);
			out.writeBytes(msg);
			return;
		}
		PacketUtil.writeVarInt(bytes, out); // Uncompressed data length
		// begin compression
		def.setInput(msg.array(), msg.arrayOffset(), bytes);
		def.finish();
		while (!def.finished()) {
			int deflated = def.deflate(buf);
			out.writeBytes(buf, 0, deflated);
		}
		def.reset();
	}

}
