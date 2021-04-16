package de.atlasmc.io;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

public interface Packet {
	
	public void read(ByteBuf in) throws IOException;
	public void write(ByteBuf out) throws IOException;
	public boolean isCancelled();
	public void setCancelled(boolean cancelled);
	
	/**
	 * 
	 * @return the protocol version
	 */
	public int getVersion();
	
	/**
	 * 
	 * @return the packet id
	 */
	public int getID();
}
