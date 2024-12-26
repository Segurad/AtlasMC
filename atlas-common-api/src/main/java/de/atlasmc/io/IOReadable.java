package de.atlasmc.io;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

@FunctionalInterface
public interface IOReadable {
	
	void read(ByteBuf buf) throws IOException;

}
