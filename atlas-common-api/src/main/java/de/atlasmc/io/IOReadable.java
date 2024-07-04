package de.atlasmc.io;

import io.netty.buffer.ByteBuf;

@FunctionalInterface
public interface IOReadable {
	
	void read(ByteBuf buf);

}
