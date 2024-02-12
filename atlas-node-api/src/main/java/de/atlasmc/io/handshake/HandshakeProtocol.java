package de.atlasmc.io.handshake;

import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketIO;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.Protocol;
import de.atlasmc.io.ProxyConnectionHandler;
import io.netty.buffer.ByteBuf;

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
	public PacketListener createDefaultPacketListener(Object o) {
		if (o instanceof ProxyConnectionHandler con)
			return new HandshakePacketListener(con, this);
		else 
			return null;
	}
	
	public void setPacketIO(int id, HandshakePaketIO<? extends PacketHandshake> io) {
		packets.put(id, io);
	}
	
	public HandshakePaketIO<?> getPacketIO(int id) {
		return packets.get(id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <P extends Packet> P readPacket(P packet, ByteBuf in, ConnectionHandler con) throws IOException {
		HandshakePaketIO<P> io = (HandshakePaketIO<P>) packets.get(packet.getID());
		if (io == null)
			return null;
		io.read(packet, in, con);
		return packet;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <P extends Packet> P writePacket(P packet, ByteBuf out, ConnectionHandler con) throws IOException {
		HandshakePaketIO<P> io = (HandshakePaketIO<P>) packets.get(packet.getID());
		if (io == null)
			return null;
		io.write(packet, out, con);
		return packet;
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
