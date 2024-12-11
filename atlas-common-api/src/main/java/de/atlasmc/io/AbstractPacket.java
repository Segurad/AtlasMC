package de.atlasmc.io;

public abstract class AbstractPacket implements Packet {
	
	private final int id;
	private boolean cancelled;
	private long timestamp;
	
	/**
	 * 
	 * @param id the packets id
	 */
	public AbstractPacket(int id) {
		this.id = id;
		this.cancelled = false;
	}
	
	/**
	 * Creates a new AbstractPacket with packet {@link #getDefaultID()}
	 */
	public AbstractPacket() {
		this.id = getDefaultID();
		this.cancelled = false;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public int getID() {
		return id;
	}
	
	@Override
	public long getTimestamp() {
		return timestamp;
	}
	
	@Override
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

}
