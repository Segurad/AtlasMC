package de.atlasmc.io.protocol.handshake;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.connection.ConnectionHandler;

public abstract class HandshakePaketIO<P extends Packet> implements PacketIO<P> {

	public abstract void handle(ConnectionHandler handler, P packet);
	
}
