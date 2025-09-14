package de.atlasmc.node;

import de.atlasmc.plugin.startup.StartupContext;
import de.atlasmc.plugin.startup.StartupHandlerRegister;
import de.atlasmc.plugin.startup.StartupStageHandler;

@StartupHandlerRegister({ StartupContext.INIT_NODE })
class InitNodeHandler implements StartupStageHandler {

	@Override
	public void prepareStage(StartupContext context) {
		context.setContex("builder", new AtlasNodeBuilder());
	}
	
	@Override
	public void handleStage(StartupContext context) {
		// not required
	}

	@Override
	public void finalizeStage(StartupContext context) {
		AtlasNodeBuilder builder = context.getContext("builder");
		AtlasNode.init(builder.build());
	}
	
}
