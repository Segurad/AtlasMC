package de.atlasmc.master;

import de.atlasmc.plugin.StartupContext;
import de.atlasmc.plugin.StartupStageHandler;

public class InitMasterHandler implements StartupStageHandler {

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
