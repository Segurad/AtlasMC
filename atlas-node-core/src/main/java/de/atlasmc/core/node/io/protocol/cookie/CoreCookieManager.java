package de.atlasmc.core.node.io.protocol.cookie;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import de.atlasmc.NamespacedKey;
import de.atlasmc.node.io.protocol.cookie.Cookie;
import de.atlasmc.node.io.protocol.cookie.CookieClient;
import de.atlasmc.node.io.protocol.cookie.CookieFactory;
import de.atlasmc.node.io.protocol.cookie.CookieManager;
import de.atlasmc.util.Pair;
import de.atlasmc.util.concurrent.future.CompletableFuture;
import de.atlasmc.util.concurrent.future.CompleteFuture;
import de.atlasmc.util.concurrent.future.Future;
import io.netty.buffer.ByteBuf;

public class CoreCookieManager implements CookieManager {

	private final Map<NamespacedKey, Cookie> cookies;
	private final CopyOnWriteArrayList<Pair<NamespacedKey, CompletableFuture<Cookie>>> requests;
	private volatile CookieClient client;
	
	public CoreCookieManager(CookieClient client) {
		setClient(client);
		this.cookies = new ConcurrentHashMap<>();
		this.requests = new CopyOnWriteArrayList<>();
	}
	
	@Override
	public Collection<Cookie> getCookies() {
		return cookies.values();
	}

	@Override
	public Cookie get(NamespacedKey key) {
		return cookies.get(key);
	}
	
	@Override
	public synchronized Future<Cookie> getOrRequest(NamespacedKey key) {
		Cookie cookie = get(key);
		if (cookie != null)
			return CompleteFuture.of(cookie);
		return request(key);
	}

	@Override
	public synchronized Future<Cookie> request(NamespacedKey key) {
		for (var pending : requests) {
			if (pending.value1().equals(key))
				return pending.value2();
		}
		var future = new CompletableFuture<Cookie>();
		requests.add(Pair.of(key, future));
		client.requestCookie(key);
		return future;
	}

	@Override
	public synchronized Cookie remove(NamespacedKey key, boolean update) {
		var cookie = cookies.remove(key);
		client.updateCookie(key, null);
		return cookie;
	}

	@Override
	public synchronized Cookie setCookie(Cookie cookie, boolean update) {
		var old = cookies.put(cookie.getNamespacedKey(), cookie);
		if (update)
			updateCookie(cookie.getNamespacedKey());
		return old;
	}

	@Override
	public void clear() {
		cookies.clear();
	}

	@Override
	public synchronized void handleCookieResponse(NamespacedKey key, ByteBuf payload) {
		Pair<NamespacedKey, CompletableFuture<Cookie>> future;
		if (requests.isEmpty()) {
			future = null;
		} else {
			future = requests.remove(0);
		}
		if (future == null)
			throw new IllegalStateException("Unprompted cookie: " + key);
		if (!future.value1().equals(key))
			throw new IllegalStateException("Cookie response out of order expected: " + future.value1() + " but recived: " + key);
		var registry = CookieFactory.REGISTRY_KEY.get();
		var factory = registry.getOrDefault(key);
		if (factory == null) {
			var error = new IllegalStateException("No cookie factory found: " + key);
			future.value2().complete(null, error);
			throw error;
		}
		var cookie = factory.createCookie(key, payload);
		setCookie(cookie, false);
		future.value2().complete(cookie);
	}

	@Override
	public boolean updateCookie(NamespacedKey key) {
		var cookie = get(key);
		if (cookie == null)
			return false;
		client.updateCookie(cookie.getClientKey(), cookie.getPayload());
		return true;
	}
	
	@Override
	public void setClient(CookieClient client) {
		this.client = Objects.requireNonNull(client, "client");
	}

}
