package de.atlasmc.io;

/**
 * A packet containing data for network operations
 */
public interface Packet {
	
	/**
	 * Whether or not this packet was handled successfully
	 * @return true if handled
	 */
	boolean isHandled();
	
	/**
	 * Sets whether or not this packet was handled
	 * @param handled
	 */
	void setHandled(boolean handled);
	
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
	 * The id used for this packet.
	 * For packets constructed using the default constructor this id should match {@link #getDefaultID()}.
	 * For packets read this id should be the id used within the protocol.
	 * @return the packet id
	 */
	int getID();
	
	/**
	 * Used to set the packet id.
	 * Do not modify except you know what you are doing.
	 * @param id
	 */
	void setID(int id);
	
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
	    return annotation.packetID();
	}
	
	/**
	 * Returns the packet definition provided by the {@link DefaultPacketID} annotation
	 */
	public static String getPacketDefinition(Class<?> clazz) {
		DefaultPacketID annotation = clazz.getAnnotation(DefaultPacketID.class);
	    if (annotation == null) 
	    	throw new IllegalArgumentException("Class does not contain DefaultPacketID annotation: " + clazz.getName());
	    return annotation.definition();
	}
	
}
