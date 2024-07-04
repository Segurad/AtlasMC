package de.atlascore;

import de.atlasmc.log.Log;
import de.atlasmc.tick.TickingThread;

public class CoreAtlasThread extends TickingThread {
	
	public CoreAtlasThread(Log logger) {
		super("Atlas-Main", 50, logger, false);
	}
	
	@Override
	public void run() {
		super.run();
	}
	
	@Override
	protected void tick(int tick) {
		
	}

}
