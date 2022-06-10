package de.atlascore.io.netty.channel;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncryptor extends MessageToByteEncoder<ByteBuf> {

	private final Cipher cip;
	
	public PacketEncryptor(byte[] secret) {
		Cipher cip = null;
		try {
			cip = Cipher.getInstance("AES/CFB8/NoPadding");
			byte[] iv = new byte[16];
			new SecureRandom().nextBytes(iv);
			cip.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(secret, "AES"), new IvParameterSpec(iv));
		} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		this.cip = cip;
	}
	
	@Override
	protected void encode(ChannelHandlerContext ctx, ByteBuf msg, ByteBuf out) throws Exception {
		int bytes = msg.readableBytes();
		int neededOut = cip.getOutputSize(bytes);
		ByteBuf outBuf = ctx.alloc().ioBuffer(neededOut);
		cip.update(msg.array(), msg.arrayOffset(), bytes, outBuf.array(), outBuf.arrayOffset());
		out.writeBytes(outBuf);
	}

}
