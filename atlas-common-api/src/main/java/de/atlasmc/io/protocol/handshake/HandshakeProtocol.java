package de.atlasmc.io.protocol.handshake;

import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.Protocol;
import de.atlasmc.io.connection.ConnectionHandler;

public class HandshakeProtocol implements Protocol {

	public static final HandshakeProtocol DEFAULT_PROTOCOL;

	private final ConcurrentHashMap<Integer, HandshakePacketIO<?>> packets;
	
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
	public PacketListener createDefaultPacketListenerServerbound(Object o) {
		return new HandshakePacketListener((ConnectionHandler) o, this);
	}
	
	@Override
	public PacketListener createDefaultPacketListenerClientbound(Object o) {
		return null;
	}
	
	public void setPacketIO(int id, HandshakePacketIO<? extends PacketHandshake> io) {
		packets.put(id, io);
	}
	
	public HandshakePacketIO<?> getPacketIO(int id) {
		return packets.get(id);
	}

	@Override
	public Packet createPacketServerbound(int id) {
		HandshakePacketIO<?> io = packets.get(id);
		if (io == null)
			return null;
		return io.createPacketData();
	}

	@Override
	public Packet createPacketClientbound(int id) {
		return createPacketServerbound(id);
	}

	@Override
	public PacketIO<? extends Packet> getHandlerServerbound(int id) {
		return packets.get(id);
	}

	@Override
	public PacketIO<? extends Packet> getHandlerClientbound(int id) {
		return getHandlerServerbound(id);
	}

}
