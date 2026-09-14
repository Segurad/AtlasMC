package de.atlasmc.network.io;

import java.util.UUID;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.network.NetworkType;
import de.atlasmc.network.io.protocol.AbstractPacketRequestType.Action;
import de.atlasmc.util.concurrent.future.Future;

public interface RequestManager {
	
	int getDefaultTimeout();
	
	void setDefaultTimeout(int timeout);
	
	<T> Future<T> request(NetworkType type, String name, Action action, int timeout);
	
	<T> Future<T> request(NetworkType type, UUID uuid, Action action, int timeout);
	
	default <T> Future<T> request(NetworkType type, String name, Action action) {
		return request(type, name, action, getDefaultTimeout());
	}
	
	default <T> Future<T> request(NetworkType type, UUID uuid, Action action) {
		return request(type, uuid, action, getDefaultTimeout());
	}
	
	default <T> Future<T> request(NetworkType type, String name) {
		return request(type, name, Action.REQUEST, getDefaultTimeout());
	}
	
	default <T> Future<T> request(NetworkType type, UUID uuid) {
		return request(type, uuid, Action.REQUEST, getDefaultTimeout());
	}
	
	<T> void setHandler(NetworkType type, RequestHandler<T> handler);
	
	StreamCodec<?> getCodec(NetworkType type);
	
	void handleResponse(int transaction, NetworkType type, Object response);

}
