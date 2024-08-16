package de.atlasmc.plugin;

public interface StartupStageHandler {
	
	/**
	 * Called to prepare a stage. Should be used to fill the context with required data e.g. Builders
	 * @param context
	 */
	default void prepareStage(StartupContext context) {}
	
	/**
	 * Called to load data and prepare initialization.
	 * Used to populate builders load data prepare initialization 
	 * @param context
	 */
	void handleStage(StartupContext context);
	
	/**
	 * Called to initialize everything
	 * @param context
	 */
	default void finalizeStage(StartupContext context) {}

}
