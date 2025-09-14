package de.atlasmc.core.node.io.protocol;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketListener;
import de.atlasmc.log.Log;

public abstract class CoreAbstractPacketListener<H, P extends Packet> implements PacketListener {

	protected H holder;
	protected final Queue<Packet> syncQueue;
	protected final int packetCount;
	
	public CoreAbstractPacketListener(H holder, int packetCount) {
		this.holder = holder;
		this.packetCount = packetCount;
		syncQueue = new ConcurrentLinkedQueue<>();
	}
	
	@Override
	public void handlePacket(Packet packet) {
		int id = packet.getID();
		if (id < 0 && id >= packetCount) {
			unhandledPacket(packet);
			return;
		}
		if (handleAsync(id)) {
			handle(packet);
		} else {
			syncQueue.add(packet);
		}
	}

	@Override
	public void handleSyncPackets(Log logger) {
		Packet packet = null;
		while ((packet = syncQueue.poll()) != null) {
			try {
				handle(packet);
			} catch(Exception e) {
				handlePacketError(logger, packet, e);
			}
		}
	}
	
	private void handlePacketError(Log error, Packet packet, Throwable cause) {
		// TODO packet error
	}
	
	@Override
	public boolean hasSyncPackets() {
		return !syncQueue.isEmpty();
	}

	@Override
	public void handleUnregister() {
		// TODO Auto-generated method stub
		
	}
	
	protected void unhandledPacket(Packet packet) {
		throw new IllegalStateException("Unhandled Packet: " + packet.getClass().getName() + "(" + packet.getID() + ")");
	}
	
	protected abstract boolean handleAsync(int packetID);
	
	protected abstract void handle(Packet packet);
	
	@FunctionalInterface
	public static interface PacketHandler<H, P extends Packet> {
		public void handle(H holder, P packet);
	}

}
