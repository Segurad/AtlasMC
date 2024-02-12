package de.atlasmc.io;

import io.netty.buffer.ByteBuf;

@FunctionalInterface
public interface IOWriteable {
	
	void write(ByteBuf buf);

}
