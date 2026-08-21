package de.atlasmc.io.netty.channel;

import java.util.List;
import java.util.Objects;

import javax.crypto.Cipher;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public final class PacketDecryptor extends MessageToMessageDecoder<ByteBuf> {

	private final Cipher cipher;
	
	public PacketDecryptor(Cipher cipher) {
		this.cipher = Objects.requireNonNull(cipher, "cipher");
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		int bytes = msg.readableBytes();
		int neededOut = cipher.getOutputSize(bytes);
		ByteBuf outBuf = ctx.alloc().ioBuffer(neededOut);
		cipher.update(msg.array(), msg.arrayOffset(), bytes, outBuf.array(), outBuf.arrayOffset());
		out.add(outBuf);
	}

}
