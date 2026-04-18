package de.atlasmc.io.protocol.handshake;

import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketCodec;
import de.atlasmc.io.Protocol;
import de.atlasmc.io.connection.ConnectionHandler;
import de.atlasmc.io.connection.PacketListener;

public class HandshakeProtocol implements Protocol {

	public static final HandshakeProtocol DEFAULT_PROTOCOL;

	private final ConcurrentHashMap<Integer, HandshakePacketCodec<?>> packets;
	
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
	
	public void setPacketIO(int id, HandshakePacketCodec<? extends PacketHandshake> io) {
		packets.put(id, io);
	}
	
	public HandshakePacketCodec<?> getPacketIO(int id) {
		return packets.get(id);
	}

	@Override
	public PacketCodec<? extends Packet> getCodecServerbound(int id) {
		return packets.get(id);
	}

	@Override
	public PacketCodec<? extends Packet> getCodecClientbound(int id) {
		return getCodecServerbound(id);
	}

	@Override
	public PacketCodec<? extends Packet> getCodecServerboundByDefault(int id) {
		return packets.get(id);
	}

	@Override
	public PacketCodec<? extends Packet> getCodecClientboundByDefault(int id) {
		return getCodecServerboundByDefault(id);
	}

}
