package de.atlascore;

import de.atlasmc.Atlas;
import de.atlasmc.atlasnetwork.LocalAtlasNode;
import de.atlasmc.log.Log;
import de.atlasmc.util.TickingThread;

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
		LocalAtlasNode node = Atlas.getAtlas();
		Atlas.getNetwork().tick();
		node.getProxies().forEach((proxy) -> {
			proxy.tick();
		});
	}

}
