package de.atlascore.atlasnetwork.master;

import de.atlascore.atlasnetwork.master.node.CoreNodeManager;
import de.atlascore.atlasnetwork.master.server.CoreServerManager;
import de.atlasmc.Atlas;
import de.atlasmc.atlasnetwork.AtlasNetwork;
import de.atlasmc.master.AtlasMaster;
import de.atlasmc.plugin.startup.StartupContext;
import de.atlasmc.plugin.startup.StartupHandlerRegister;
import de.atlasmc.plugin.startup.StartupStageHandler;

@StartupHandlerRegister(value = { StartupContext.CONNECT_MASTER })
class CoreAtlasNetworkConnectMasterStageHandler implements StartupStageHandler {

	@Override
	public void prepareStage(StartupContext context) {
		context.setContex("builder", new CoreAtlasNetworkHandlerBuilder());
	}
	
	@Override
	public void handleStage(StartupContext context) {
		CoreAtlasNetworkHandlerBuilder builder = context.getContext("builder");
		builder.setNodeManager(new CoreNodeManager())
			.setServerManager(new CoreServerManager(AtlasMaster.getServerManager()))
			.setProfileHandler(AtlasMaster.getProfileManager())
			.setPermissionManager(AtlasMaster.getPermissionManager())
			.setUUID(AtlasMaster.getUUID())
			.setPublicKey(Atlas.getKeyPair().getPublic());
	}
	
	@Override
	public void finalizeStage(StartupContext context) {
		CoreAtlasNetworkHandlerBuilder builder = context.getContext("builder");
		AtlasNetwork.init(builder.build());
	}

}
