package de.atlasmc.core.network.io;

import de.atlasmc.network.NetworkType;
import de.atlasmc.network.io.protocol.AbstractPacketRequestType.Action;
import de.atlasmc.util.concurrent.future.CompletableFuture;

public class CoreRequestFuture<V> extends CompletableFuture<V> {

	public final long timeout;
	public final int transactionID;
	public final int hash;
	public final NetworkType type;
	public final Object query;
	
	public CoreRequestFuture(long timeout, int transaction, int hash, NetworkType type, Object query) {
		this.timeout = timeout;
		this.transactionID = transaction;
		this.hash = hash;
		this.type = type;
		this.query = query;
	}
	
}
