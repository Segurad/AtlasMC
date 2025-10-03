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
	public void writeQueuedPackets() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasQueued() {
		// TODO Auto-generated method stub
		return false;
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEncryotionEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Log getLogger() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDecompression(boolean enable) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasCompression() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void setCompression(boolean enbale) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean hasDecompression() {
		// TODO Auto-generated method stub
		return false;
	}

}
