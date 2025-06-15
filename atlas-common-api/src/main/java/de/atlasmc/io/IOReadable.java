package de.atlasmc.io;

import java.io.IOException;

import de.atlasmc.util.annotation.NotNull;
import io.netty.buffer.ByteBuf;

@FunctionalInterface
public interface IOReadable {
	
	void read(@NotNull ByteBuf buf) throws IOException;

}
