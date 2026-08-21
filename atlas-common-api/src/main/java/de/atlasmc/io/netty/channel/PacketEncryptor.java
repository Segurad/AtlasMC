package de.atlasmc.io.netty.channel;

import java.util.Objects;

import javax.crypto.Cipher;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncryptor extends MessageToByteEncoder<ByteBuf> {

	private final Cipher cipher;
	
	public PacketEncryptor(Cipher cipher) {
		this.cipher = Objects.requireNonNull(cipher, "cipher");
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
		int bytes = msg.readableBytes();
		int neededOut = cipher.getOutputSize(bytes);
		ByteBuf outBuf = ctx.alloc().ioBuffer(neededOut);
		cipher.update(msg.array(), msg.arrayOffset(), bytes, outBuf.array(), outBuf.arrayOffset());
		out.writeBytes(outBuf);
	}

}
