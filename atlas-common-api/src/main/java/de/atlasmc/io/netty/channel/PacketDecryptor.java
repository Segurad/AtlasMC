package de.atlasmc.io.netty.channel;

import java.util.List;

import javax.crypto.Cipher;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public final class PacketDecryptor extends MessageToMessageDecoder<ByteBuf> {

	private final PacketEncryptionHandler handler;
	
	public PacketDecryptor(Cipher cipher) {
		this.handler = new PacketEncryptionHandler(cipher);
	}
	
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		out.add(handler.decode(msg, ctx));
	}

}
