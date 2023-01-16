package de.atlasmc.io;

import java.io.IOException;

import de.atlascore.io.ConnectionHandler;
import io.netty.buffer.ByteBuf;

public interface Protocol {
	
	public Packet createPacketIn(int id);
	
	public Packet createPacketOut(int id);
	
	public <P extends Packet> P readPacket(P packet, ByteBuf in, ConnectionHandler con) throws IOException;
	
	public <P extends Packet> P writePacket(P packet, ByteBuf out, ConnectionHandler con) throws IOException;
	
	public int getVersion();
	
	public PacketListener createDefaultPacketListener(Object o);

}
