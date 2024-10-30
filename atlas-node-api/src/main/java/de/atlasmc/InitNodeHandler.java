package de.atlasmc;

import de.atlasmc.plugin.startup.StartupContext;
import de.atlasmc.plugin.startup.StartupStageHandler;

class InitNodeHandler implements StartupStageHandler {

	@Override
	public void prepareStage(StartupContext context) {
		context.setContex("builder", new AtlasNodeBuilder());
	}
	
	@Override
	public void handleStage(StartupContext context) {}

	@Override
	public void finalizeStage(StartupContext context) {
		AtlasNodeBuilder builder = context.getContext("builder");
		AtlasNode.init(builder.build());
	}
	
}
