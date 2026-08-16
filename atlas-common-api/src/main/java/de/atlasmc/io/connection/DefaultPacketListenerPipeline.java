package de.atlasmc.io.connection;

import de.atlasmc.io.Packet;
import de.atlasmc.util.pipeline.AbstractConcurrentPipeline;

public class DefaultPacketListenerPipeline extends AbstractConcurrentPipeline<PacketListener> implements PacketListenerPipeline {

	private static final PacketListener[] EMPTY = {};
	
	@Override
	protected String getEntryName(String name, PacketListener listener) {
		return name != null ? name : "Listener-" + listener.getClass().getName();
	}

	@Override
	public boolean handlePacket(final ConnectionHandler handler, final Packet packet) {
		final var listeners = this.entries;
		final int count = listeners.length;
		if (count == 0)
			return false;
		boolean handled = false;
		for (int i = 0; i < count; i++) {
			final PacketListener listener = listeners[i];
			try {
				handled = listener.handlePacket(handler, packet, handled);
			} catch (Exception e) {
				handler.handleException(e);
			}
		}
		return handled;
	}
	
	@Override
	public boolean handlePacketSync(final ConnectionHandler handler, final Packet packet) {
		final var listeners = this.entries;
		final int count = listeners.length;
		if (count == 0)
			return false;
		boolean handled = false;
		for (int i = 0; i < count; i++) {
			final PacketListener listener = listeners[i];
			try {
				handled = listener.handlePacketSync(handler, packet, handled);
			} catch (Exception e) {
				handler.handleException(e);
			}
		}
		return handled;
	}

	@Override
	protected PacketListener[] getEmpty() {
		return EMPTY;
	}

}
