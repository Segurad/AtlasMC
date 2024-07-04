package de.atlasmc.util;

public interface WatchableThread {
	
	long lastHeartBeat();
	
	Thread getThread();

}
