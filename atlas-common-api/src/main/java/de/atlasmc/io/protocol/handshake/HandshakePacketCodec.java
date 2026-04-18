package de.atlasmc.io.protocol.handshake;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.connection.ConnectionHandler;

public abstract class HandshakePacketCodec<P extends Packet> implements PacketCodec<P> {

	public abstract void handle(ConnectionHandler handler, P packet);
	
}
