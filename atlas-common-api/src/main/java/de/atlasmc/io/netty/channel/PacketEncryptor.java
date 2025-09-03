package de.atlasmc.io.netty.channel;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import de.atlasmc.io.ProtocolException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketEncryptor extends MessageToByteEncoder<ByteBuf> {

	private final Cipher cip;
	
	public PacketEncryptor(SecretKey secret) throws InvalidKeyException, InvalidAlgorithmParameterException {
		Cipher cip = null;
		try {
			cip = Cipher.getInstance("AES/CFB8/NoPadding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			throw new ProtocolException("Unable to find AES cipher!");
		}
		cip.init(Cipher.ENCRYPT_MODE, secret, new IvParameterSpec(secret.getEncoded()));
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
