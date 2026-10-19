package de.atlasmc.io.netty.channel;

import javax.crypto.Cipher;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncryptor extends MessageToByteEncoder<ByteBuf> {

	private final PacketEncryptionHandler handler;
	
	public PacketEncryptor(Cipher cipher) {
		this.handler = new PacketEncryptionHandler(cipher);
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
		handler.encode(msg, out);
	}

}
