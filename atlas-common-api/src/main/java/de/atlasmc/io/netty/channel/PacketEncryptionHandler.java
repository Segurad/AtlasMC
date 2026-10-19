package de.atlasmc.io.netty.channel;

import java.util.Objects;

import javax.crypto.Cipher;
import javax.crypto.ShortBufferException;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import it.unimi.dsi.fastutil.bytes.ByteArrays;

public class PacketEncryptionHandler {
	
	private final Cipher cipher;
	private byte[] inBuf = ByteArrays.EMPTY_ARRAY;
	private byte[] outBuf = ByteArrays.EMPTY_ARRAY;
	
	public PacketEncryptionHandler(Cipher cipher) {
		this.cipher = Objects.requireNonNull(cipher, "cipher");
	}
	
	private byte[] readByteBuf(ByteBuf buf) {
		var bytes = buf.readableBytes();
		var inBuf = this.inBuf;
		if (inBuf.length < bytes)
			this.inBuf = inBuf = new byte[bytes];
		buf.readBytes(inBuf, 0, bytes);
		return inBuf;
	}
	
	/**
	 * Decodes the input buffer and returns a newly allocated output buffer using the context for allocation
	 * @param in
	 * @param ctx
	 * @return buffer
	 * @throws ShortBufferException 
	 */
	public ByteBuf decode(ByteBuf in, ChannelHandlerContext ctx) throws ShortBufferException {
		int bytes = in.readableBytes();
		var inBuf = readByteBuf(in);
		int neededOut = cipher.getOutputSize(bytes);
		ByteBuf out = ctx.alloc().heapBuffer(neededOut);
		try {
			var size = cipher.update(inBuf, 0, bytes, out.array(), out.arrayOffset());
			out.writerIndex(size);
			return out;
		} catch (ShortBufferException | RuntimeException e) {
			out.release();
			throw e;
		}
	}
	
	/**
	 * Encodes the input buffer and writes it to the out buffer
	 * @param in
	 * @param out
	 * @throws ShortBufferException
	 */
	public void encode(ByteBuf in, ByteBuf out) throws ShortBufferException {
		int bytes = in.readableBytes();
		var inBuf = readByteBuf(in);
		int neededOut = cipher.getOutputSize(bytes);
		var outBuf = this.outBuf;
		if (outBuf.length < neededOut)
			this.outBuf = outBuf = new byte[neededOut];
		var size = cipher.update(inBuf, 0, bytes, outBuf, 0);
		out.writeBytes(outBuf, 0, size);
	}

}
