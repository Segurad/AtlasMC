package de.atlasmc.io;

import java.io.IOException;

import de.atlasmc.util.annotation.NotNull;
import io.netty.buffer.ByteBuf;

@FunctionalInterface
public interface IOWriteable {
	
	void write(@NotNull ByteBuf buf) throws IOException;

}
