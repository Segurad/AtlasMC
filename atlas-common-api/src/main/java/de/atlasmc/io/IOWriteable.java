package de.atlasmc.io;

import de.atlasmc.util.annotation.NotNull;
import io.netty.buffer.ByteBuf;

@FunctionalInterface
public interface IOWriteable {
	
	void write(@NotNull ByteBuf buf);

}
