package de.atlasmc.io.handshake;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentHashMap;

import de.atlasmc.io.ConnectionHandler;
import de.atlasmc.io.Packet;
import de.atlasmc.io.PacketListener;
import de.atlasmc.io.Protocol;

public class HandshakeProtocol implements Protocol {

	private final ConcurrentHashMap<Integer, Class<? extends Packet>> packets;
	public static final HandshakeProtocol DEFAULT_PROTOCOL;
	
	static {
		DEFAULT_PROTOCOL = new HandshakeProtocol();
		DEFAULT_PROTOCOL.setPacket(0x00, PacketMinecraftHandshake.class);
		DEFAULT_PROTOCOL.setPacket(0x01, PacketAtlasPlayerHandshake.class);
		DEFAULT_PROTOCOL.setPacket(0x02, PacketAtlasNodeHandshake.class);
		DEFAULT_PROTOCOL.setPacket(0xFE, PacketMinecraftLegacyHandshake.class);
	}
	
	public HandshakeProtocol() {
		packets = new ConcurrentHashMap<>(4);
	}
	
	@Override
	public Packet createPacketIn(int id) {
		Class<? extends Packet> clazz = packets.get(id);
		if (clazz == null) return null;
		try {
			return clazz.getConstructor(null).newInstance(null);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Packet createPacketOut(int id) {
		return null;
	}

	@Override
	public int getVersion() {
		return 0;
	}

	@Override
	public PacketListener createDefaultPacketListener(Object o) {
		if (ConnectionHandler.class.isInstance(o))
		return new HandshakePacketListener((ConnectionHandler) o);
		else return null;
	}

	@Override
	public Packet createCopy(Packet packet) {
		return null;
	}
	
	public void setPacket(int id, Class<? extends Packet> clazz) {
		packets.put(id, clazz);
	}

	@Override
	public <T extends Packet> T createPacket(Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

}
