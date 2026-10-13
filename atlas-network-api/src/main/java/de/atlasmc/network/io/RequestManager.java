package de.atlasmc.network.io;

import java.util.UUID;

import de.atlasmc.io.codec.StreamCodec;
import de.atlasmc.network.NetworkType;
import de.atlasmc.util.concurrent.future.Future;

/**
 * Manager for requesting data from the Network and handling updates
 */
public interface RequestManager {
	
	int getDefaultTimeout();
	
	void setDefaultTimeout(int timeout);
	
	<T> Future<T> request(NetworkType type, String name, RequestAction action, int timeout);
	
	<T> Future<T> request(NetworkType type, UUID uuid, RequestAction action, int timeout);
	
	<T> Future<T> request(NetworkType type, int id, RequestAction action, int timeout);
	
	default <T> Future<T> request(NetworkType type, String name, RequestAction action) {
		return request(type, name, action, getDefaultTimeout());
	}
	
	default <T> Future<T> request(NetworkType type, UUID uuid, RequestAction action) {
		return request(type, uuid, action, getDefaultTimeout());
	}
	
	default <T> Future<T> request(NetworkType type, int id, RequestAction action) {
		return request(type, id, action, getDefaultTimeout());
	}
	
	default <T> Future<T> request(NetworkType type, String name) {
		return request(type, name, RequestAction.REQUEST, getDefaultTimeout());
	}
	
	default <T> Future<T> request(NetworkType type, UUID uuid) {
		return request(type, uuid, RequestAction.REQUEST, getDefaultTimeout());
	}
	
	default <T> Future<T> request(NetworkType type, int id) {
		return request(type, id, RequestAction.REQUEST, getDefaultTimeout());
	}
	
	<T> void setHandler(NetworkType type, RequestHandler<T> handler);
	
	StreamCodec<?> getCodec(NetworkType type);
	
	void handleResponse(int transaction, NetworkType type, Object response);

}
