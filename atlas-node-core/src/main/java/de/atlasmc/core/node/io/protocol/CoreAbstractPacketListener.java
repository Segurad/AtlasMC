package de.atlasmc.core.node.io.protocol;

import java.io.IOException;

import de.atlasmc.io.Packet;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.io.connection.PacketListener;

public abstract class CoreAbstractPacketListener<H, P extends Packet> implements PacketListener {

	protected H holder;
	protected final int packetCount;
	protected final boolean ignoreHandled;
	
	public CoreAbstractPacketListener(H holder, int packetCount, boolean ignoreHandled) {
		this.holder = holder;
		this.packetCount = packetCount;
		this.ignoreHandled = ignoreHandled;
	}
	
	@Override
	public boolean handlePacket(ConnectionHandler handler, Packet packet, boolean handled) {
		if (handled && ignoreHandled)
			return handled;
		int id = packet.getID();
		if (id < 0 && id >= packetCount) {
			return handled;
		}
		if (!handleAsync(id))
			return handled;
			handle(packet);
		return true;
	}
	
	@Override
	public boolean handlePacketSync(ConnectionHandler handler, Packet packet, boolean handled) throws IOException {
		if (handled && ignoreHandled)
			return handled;
		int id = packet.getID();
		if (id < 0 && id >= packetCount) {
			return handled;
		}
		handle(packet);
		return true;
	}
	
	protected abstract boolean handleAsync(int packetID);
	
	protected abstract void handle(Packet packet);
	
	@FunctionalInterface
	public static interface PacketHandler<H, P extends Packet> {
		public void handle(H holder, P packet);
	}

}
