package de.atlasmc.io.connection;

import java.net.InetSocketAddress;

import javax.crypto.Cipher;

import de.atlasmc.io.Packet;
import de.atlasmc.io.Protocol;
import de.atlasmc.log.Log;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;

public class InternalConnectionHandler extends AbstractConnectionHandler {

	private static final InetSocketAddress ADDRESS = InetSocketAddress.createUnresolved("localhost", 0);
	
	private volatile boolean inboundTerminated;
	private volatile boolean outboundTerminated;
	private volatile boolean closed;
	
	public InternalConnectionHandler(Log log, Protocol protocol) {
		super(log, protocol);
	}

	@Override
	public InetSocketAddress getRemoteAddress() {
		return ADDRESS;
	}

	@Override
	public void sendPacket(Packet packet, GenericFutureListener<? extends Future<? super Void>> listener) {
		throw new UnsupportedOperationException("Not implemented yet");
	}

	@Override
	public synchronized void close() {
		if (closed)
			return;
		closed = true;
	}

	@Override
	public boolean isClosed() {
		return closed;
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
		return inboundTerminated;
	}

	@Override
	public boolean isOutboundTerminated() {
		return outboundTerminated;
	}

	@Override
	public void enableEncryption(Cipher encription, Cipher decription) {
		// not required
	}

}
