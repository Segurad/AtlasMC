package de.atlasmc.core.node.io.protocol;

import de.atlasmc.io.Packet;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.io.connection.PacketListener;

public abstract class CoreAbstractPacketListener<H, P extends Packet> implements PacketListener {

	protected H holder;
	protected final int packetCount;
	
	public CoreAbstractPacketListener(H holder, int packetCount) {
		this.holder = holder;
		this.packetCount = packetCount;
	}
	
	@Override
	public void handlePacket(ConnectionHandler handler, Packet packet) {
		int id = packet.getID();
		if (id < 0 && id >= packetCount) {
			return;
		}
		if (handleAsync(id)) {
			handle(packet);
		}
	}
	
	@Override
	public void handlePacketSync(ConnectionHandler handler, Packet packet) {
		int id = packet.getID();
		if (id < 0 && id >= packetCount) {
			return;
		}
		handle(packet);
	}
	
	protected abstract boolean handleAsync(int packetID);
	
	protected abstract void handle(Packet packet);
	
	@FunctionalInterface
	public static interface PacketHandler<H, P extends Packet> {
		public void handle(H holder, P packet);
	}

}
