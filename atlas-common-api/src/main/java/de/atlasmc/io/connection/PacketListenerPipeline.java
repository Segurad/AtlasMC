package de.atlasmc.io.connection;

import de.atlasmc.io.Packet;
import de.atlasmc.util.annotation.ThreadSafe;
import de.atlasmc.util.pipeline.Pipeline;

@ThreadSafe
public interface PacketListenerPipeline extends Pipeline<PacketListener> {
	
	void handlePacket(ConnectionHandler handler, Packet packet);
	
	void handlePacketSync(ConnectionHandler handler, Packet packet);
	
}
