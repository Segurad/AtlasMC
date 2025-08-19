package de.atlasmc.util;

import de.atlasmc.util.annotation.NotNull;

public interface WatchableThread {
	
	long lastHeartBeat();
	
	@NotNull
	Thread getThread();

}
