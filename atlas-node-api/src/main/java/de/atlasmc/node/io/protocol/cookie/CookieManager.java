package de.atlasmc.node.io.protocol.cookie;

import java.util.Collection;
import com.google.errorprone.annotations.ThreadSafe;

import de.atlasmc.NamespacedKey;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.concurrent.future.Future;
import io.netty.buffer.ByteBuf;

@ThreadSafe
public interface CookieManager {
	
	@NotNull
	Collection<Cookie> getCookies();
	
	@Nullable
	Cookie get(NamespacedKey key);
	
	@NotNull
	Future<Cookie> getOrRequest(NamespacedKey key);
	
	@NotNull
	Future<Cookie> request(NamespacedKey key);
	
	default Cookie remove(NamespacedKey key) {
		return remove(key, true);
	}
	
	Cookie remove(NamespacedKey key, boolean update);
	
	default Cookie setCookie(Cookie cookie) {
		return setCookie(cookie, true);
	}
	
	Cookie setCookie(Cookie cookie, boolean update);
	
	boolean updateCookie(NamespacedKey key);
	
	void clear();

	/**
	 * Sets the client this manager interacts with
	 * @param handler
	 */
	void setClient(CookieClient client);
	
	/**
	 * Handles a cookie response send by the client
	 * @param key of the cookie
	 * @param payload of the cookie may be null in case there is no cookie
	 */
	void handleCookieResponse(@NotNull NamespacedKey key, @Nullable ByteBuf payload);

}
