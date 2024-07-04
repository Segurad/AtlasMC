package de.atlascore.server;

public interface CoreLocalServerTask {
	
	String name();
	
	/**
	 * Called when this task is executed.
	 * The provided tick may be -1 if this task is executed outside of the normal tick process
	 * @param tick
	 */
	void execute(int tick) throws Exception;

}
