package de.atlasmc.io.handshake;

import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.Protocol;

public class HandshakeProtocol implements Protocol {

	public static final HandshakeProtocol DEFAULT_PROTOCOL;

	private final ConcurrentHashMap<Integer, HandshakePaketIO<?>> packets;
	
	static {
		DEFAULT_PROTOCOL = new HandshakeProtocol();
	}
	
	public HandshakeProtocol() {
		packets = new ConcurrentHashMap<>(4);
	}

	@Override
	public int getVersion() {
		return 0;
	}

	@Override
	public PacketListener createDefaultPacketListenerIn(Object o) {
		return new HandshakePacketListener((ConnectionHandler) o, this);
	}
	
	@Override
	public PacketListener createDefaultPacketListenerOut(Object o) {
		return null;
	}
	
	public void setPacketIO(int id, HandshakePaketIO<? extends PacketHandshake> io) {
		packets.put(id, io);
	}
	
	public HandshakePaketIO<?> getPacketIO(int id) {
		return packets.get(id);
	}

	@Override
	public Packet createPacketIn(int id) {
		HandshakePaketIO<?> io = packets.get(id);
		if (io == null)
			return null;
		return io.createPacketData();
	}

	@Override
	public Packet createPacketOut(int id) {
		return createPacketIn(id);
	}

	@Override
	public PacketIO<? extends Packet> getHandlerIn(int id) {
		return packets.get(id);
	}

	@Override
	public PacketIO<? extends Packet> getHandlerOut(int id) {
		return getHandlerIn(id);
	}

}
