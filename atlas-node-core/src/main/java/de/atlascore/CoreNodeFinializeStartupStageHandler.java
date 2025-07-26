package de.atlascore;

import de.atlasmc.AtlasNode;
import de.atlasmc.atlasnetwork.AtlasNode.NodeStatus;
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
