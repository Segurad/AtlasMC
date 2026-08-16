package de.atlasmc.io;

public abstract class AbstractPacket implements Packet {
	
	private int id;
	private long timestamp;
	
	/**
	 * 
	 * @param id the packets id
	 */
	public AbstractPacket(int id) {
		this.id = id;
	}
	
	/**
	 * Creates a new AbstractPacket with packet {@link #getDefaultID()}
	 */
	public AbstractPacket() {
		this.id = getDefaultID();

	}

	@Override
	public int getID() {
		return id;
	}
	
	@Override
	public void setID(int id) {
		this.id = id;
	}
	
	@Override
	public long getTimestamp() {
		return timestamp;
	}
	
	@Override
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override
	public boolean isTerminating() {
		return false;
	}
	
	@Override
	public String toString() {
		return Packet.getPacketDefinition(getClass()) + "(" + getDefaultID() + "|" + getID() + "): " + getClass().getName();
	}

}
