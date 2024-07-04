package de.atlascore.atlasnetwork.master;

import de.atlascore.atlasnetwork.master.node.CoreNodeManager;
import de.atlascore.atlasnetwork.master.server.CoreServerManager;
import de.atlasmc.log.Log;
import de.atlasmc.tick.TickingThread;

public class CoreAtlasMasterThread extends TickingThread {

	private CoreServerManager serverManager;
	private CoreNodeManager nodeManager;
	
	public CoreAtlasMasterThread(CoreAtlasNetwork network, Log logger) {
		super("Atlas-Master", 50, logger);
		this.serverManager = network.getServerManager();
		this.nodeManager = network.getNodeManager();
	}
	
	@Override
	protected void tick(int tick) {
		serverManager.tick();
		nodeManager.tick();
	}

}
