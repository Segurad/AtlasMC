package de.atlascore.atlasnetwork.master;

import de.atlascore.atlasnetwork.CorePermissionProvider;
import de.atlascore.atlasnetwork.CorePlayerProfileHandler;
import de.atlascore.atlasnetwork.master.node.CoreNodeManager;
import de.atlascore.atlasnetwork.master.server.CoreServerManager;
import de.atlasmc.log.Logging;
import de.atlasmc.util.TickingThread;

public class CoreAtlasMasterThread extends TickingThread {

	private CoreServerManager serverManager;
	private CoreNodeManager nodeManager;
	private CorePermissionProvider permProvider;
	private CorePlayerProfileHandler profileHandler;
	
	public CoreAtlasMasterThread(CoreAtlasNetwork network) {
		super("Atlas-Master", 50, Logging.getLogger("Atlas-Master", "Atlas-Master"));
		this.serverManager = network.getServerManager();
		this.nodeManager = network.getNodeManager();
		this.permProvider = network.getPermissionProvider();
		this.profileHandler = network.getProfileHandler();
	}
	
	@Override
	protected void tick(int tick) {
		serverManager.tick();
		nodeManager.tick();
		profileHandler.expungeStaleEntries();
		permProvider.expungeStaleEntries();
	}

}
