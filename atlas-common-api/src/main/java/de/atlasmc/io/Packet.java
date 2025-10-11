package de.atlasmc.io;

/**
 * A packet containing data for network operations
 */
public interface Packet {
	
	/**
	 * Whether or not this packet is cancelled
	 * @return
	 */
	boolean isCancelled();
	
	void setCancelled(boolean cancelled);
	
	/**
	 * Return the Time this Packet arrived in milliseconds and 0 if it is outbound
	 * @return 
	 */
	long getTimestamp();
	
	/**
	 * Sets the time when the packet arrived
	 * @param timestamp
	 */
	void setTimestamp(long timestamp);
	
	/**
	 * The id used for this packet
	 * @return the packet id
	 */
	int getID();
	
	/**
	 * The id used by the latest supported protocol version
	 * @return the packet id based on latest supported protocol
	 */
	int getDefaultID();
	
	/**
	 * Whether or not this packet will terminate packet processing for protocol change
	 * @return true if terminating
	 */
	boolean isTerminating();
	
	/**
	 * Returns the packet id provided by the {@link DefaultPacketID} annotation
	 */
	public static int getDefaultPacketID(Class<?> clazz) {
		DefaultPacketID annotation = clazz.getAnnotation(DefaultPacketID.class);
	    if (annotation == null) 
	    	throw new IllegalArgumentException("Class does not contain DefaultPacketID annotation: " + clazz.getName());
	    int id = annotation.packetID();
	    return id;
	}
	
}
