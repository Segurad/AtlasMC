package de.atlasmc.io;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

public interface Protocol {
	
	PacketIO<? extends Packet> getHandlerIn(int id);
	
	PacketIO<? extends Packet> getHandlerOut(int id);
	
	Packet createPacketIn(int id);
	
	Packet createPacketOut(int id);
	
	<P extends Packet> P readPacket(P packet, ByteBuf in, ConnectionHandler con) throws IOException;
	
	<P extends Packet> P writePacket(P packet, ByteBuf out, ConnectionHandler con) throws IOException;
	
	int getVersion();
	
	PacketListener createDefaultPacketListener(Object o);

}
