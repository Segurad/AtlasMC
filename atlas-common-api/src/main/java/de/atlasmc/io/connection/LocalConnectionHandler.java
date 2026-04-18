package de.atlasmc.io.connection;

import java.net.InetSocketAddress;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;

import javax.crypto.SecretKey;

import de.atlasmc.io.Packet;
import de.atlasmc.io.Protocol;
import de.atlasmc.log.Log;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class LocalConnectionHandler extends AbstractConnectionHandler {

	public LocalConnectionHandler(Log log, Protocol protocol) {
		super(log, protocol);
	}

	@Override
	public InetSocketAddress getRemoteAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void sendPacket(Packet packet, GenericFutureListener<? extends Future<? super Void>> listener) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void close() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isClosed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void enableEncryption(SecretKey secret) throws InvalidKeyException, InvalidAlgorithmParameterException {
		// not required
	}

	@Override
	public boolean isEncryotionEnabled() {
		return false;
	}

	@Override
	public void setDecompression(boolean enable) {
		// not required
	}

	@Override
	public boolean hasCompression() {
		return false;
	}

	@Override
	public void setCompression(boolean enbale) {
		// not required
	}

	@Override
	public boolean hasDecompression() {
		return false;
	}

	@Override
	public boolean isInboundTerminated() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOutboundTerminated() {
		// TODO Auto-generated method stub
		return false;
	}

}
