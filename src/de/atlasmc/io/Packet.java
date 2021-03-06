package de.atlasmc.io;

import java.io.IOException;

import io.netty.buffer.ByteBuf;

public interface Packet {
	
	public void read(ByteBuf in) throws IOException;
	public void write(ByteBuf out) throws IOException;
	public boolean isCancelled();
	public void setCancelled(boolean cancelled);
	
	/**
	 * Return the Time this Packet arrived in milliseconds and 0 if it is outbound
	 * @return 
	 */
	public long getTimestamp();
	
	public void setTimestamp(long timestamp);
	
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
	
	/**
	 * 
	 * @return the packet id based on latest supported protocol (754 | v1.16.5)
	 */
	public int getDefaultID();
	
}
