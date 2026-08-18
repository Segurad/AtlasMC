package de.atlasmc.node.io.protocol.cookie;

import de.atlasmc.NamespacedKey.Namespaced;
import io.netty.buffer.ByteBuf;

public interface Cookie extends Namespaced {
	
	void setPayload(ByteBuf payload);
	
	ByteBuf getPayload();

}
