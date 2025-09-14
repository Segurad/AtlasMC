package de.atlasmc.core.node;

import de.atlasmc.network.AtlasNode.NodeStatus;
import de.atlasmc.node.AtlasNode;
import de.atlasmc.plugin.startup.StartupContext;
import de.atlasmc.plugin.startup.StartupHandlerRegister;
import de.atlasmc.plugin.startup.StartupStageHandler;

@StartupHandlerRegister({ StartupContext.FINALIZE_STARTUP })
class CoreNodeFinializeStartupStageHandler implements StartupStageHandler {

	@Override
	public void handleStage(StartupContext context) {
		// not required
	}
	
	@Override
	public void finalizeStage(StartupContext context) {
		AtlasNode.getAtlas().setStatus(NodeStatus.ONLINE);
	}

}
