package de.atlasmc.master;

import de.atlasmc.plugin.startup.StartupContext;
import de.atlasmc.plugin.startup.StartupHandlerRegister;
import de.atlasmc.plugin.startup.StartupStageHandler;

@StartupHandlerRegister({ StartupContext.INIT_MASTER })
class InitMasterHandler implements StartupStageHandler {

	@Override
	public void prepareStage(StartupContext context) {
		context.setContex("builder", new AtlasMasterBuilder());
	}
	
	@Override
	public void handleStage(StartupContext context) {}
	
	@Override
	public void finalizeStage(StartupContext context) {
		AtlasMasterBuilder builder = context.getContext("builder");
		builder.build();
	}

}
