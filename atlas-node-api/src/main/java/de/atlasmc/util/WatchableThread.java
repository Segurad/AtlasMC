package de.atlasmc.util;

public interface WatchableThread {
	
	public long lastHeartBeat();
	
	public Thread getThread();

}
