package de.atlasmc.core.network.master;

import de.atlasmc.Atlas;
import de.atlasmc.core.network.io.atlasprotocol.CoreAtlasProtocol;
import de.atlasmc.core.network.master.node.CoreNodeManager;
import de.atlasmc.core.network.master.server.CoreServerManager;
import de.atlasmc.io.connection.LocalConnectionHandler;
import de.atlasmc.master.AtlasMaster;
import de.atlasmc.network.AtlasNetwork;
import de.atlasmc.plugin.startup.StartupContext;
import de.atlasmc.plugin.startup.StartupHandlerRegister;
import de.atlasmc.plugin.startup.StartupStageHandler;

@StartupHandlerRegister(value = { StartupContext.CONNECT_MASTER })
class CoreAtlasNetworkConnectMasterStageHandler implements StartupStageHandler {

	@Override
	public void prepareStage(StartupContext context) {
		context.getLogger().info("Connecting to master...");
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
			.setPublicKey(Atlas.getKeyPair().getPublic())
			.setConnection(new LocalConnectionHandler(context.getLogger(), CoreAtlasProtocol.INSTANCE));
	}
	
	@Override
	public void finalizeStage(StartupContext context) {
		CoreAtlasNetworkHandlerBuilder builder = context.getContext("builder");
		AtlasNetwork.init(builder.build());
		context.getLogger().info("Connected to master successfully...");
	}

}
