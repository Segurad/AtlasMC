package de.atlasmc.io.connection;

import de.atlasmc.io.Packet;
import de.atlasmc.util.annotation.ThreadSafe;

@ThreadSafe
public interface PacketListenerPipeline {
	
	PacketListener get(String name);
	
	boolean addFirst(String name, PacketListener listener);
	
	boolean addLast(String name, PacketListener listener);
	
	boolean addBefore(String before, String name, PacketListener listener);
	
	boolean addAfter(String after, String name, PacketListener listener);
	
	boolean replace(String old, String name, PacketListener listener);
	
	boolean remove(PacketListener listener);
	
	boolean remove(String name);
	
	boolean removeListeners();

	boolean hasListeners();
	
	int getCount();
	
	void handlePacket(ConnectionHandler handler, Packet packet);
	
}
