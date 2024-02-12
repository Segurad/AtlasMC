package de.atlasmc.io.handshake;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;

public abstract class HandshakePaketIO<P extends Packet> implements PacketIO<P> {

	public abstract void handle(ConnectionHandler handler, P packet);
	
}
