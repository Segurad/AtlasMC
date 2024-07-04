package de.atlasmc.io;

public interface Packet {
	
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
	 * @return the packet id
	 */
	public int getID();
	
	/**
	 * 
	 * @return the packet id based on latest supported protocol (754 | v1.16.5)
	 */
	public int getDefaultID();
	
	/**
	 * Returns the packet id provided by the {@link DefaultPacketID} annotation
	 */
	public static int getDefaultPacketID(Class<?> clazz) {
		DefaultPacketID annotation = clazz.getAnnotation(DefaultPacketID.class);
	    if (annotation == null) 
	    	throw new IllegalArgumentException("Class does not contain DefaultPacketID annotation: " + clazz.getName());
	    int id = annotation.value();
	    return id;
	}
	
}
