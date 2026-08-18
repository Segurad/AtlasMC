package de.atlasmc.node.io.protocol.cookie;

import java.util.Objects;

import de.atlasmc.NamespacedKey;
import io.netty.buffer.ByteBuf;

public class RawCookie implements Cookie {

	private final NamespacedKey key;
	private ByteBuf payload;
	
	public RawCookie(NamespacedKey key) {
		this.key = Objects.requireNonNull(key);
	}
	
	@Override
	public NamespacedKey getNamespacedKey() {
		return key;
	}

	@Override
	public void setPayload(ByteBuf data) {
		this.payload = data;
	}

	@Override
	public ByteBuf getPayload() {
		return payload;
	}

}
