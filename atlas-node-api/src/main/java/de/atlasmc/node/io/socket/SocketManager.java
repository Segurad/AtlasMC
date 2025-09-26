package de.atlasmc.node.io.socket;

import java.util.Collection;
import java.util.UUID;

import de.atlasmc.io.socket.SocketConfig;
import de.atlasmc.util.annotation.NotNull;
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.annotation.ThreadSafe;

@ThreadSafe
public interface SocketManager {
	
	@Nullable
	NodeSocket getSocket(@NotNull UUID uuid);
	
	boolean registerSocket(@NotNull NodeSocket socket);
	
	boolean unregisterSocket(@NotNull NodeSocket socket);
	
	@NotNull
	Collection<NodeSocket> getSockets();
	
	@NotNull
	NodeSocket createSocket(@NotNull SocketConfig config);

}
