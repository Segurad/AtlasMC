package de.atlasmc.tick;

public interface AtlasThreadTask {
	
	String name();
	
	/**
	 * Called by the thread this tasked is registered.
	 * The given tick indicates the current tick of the thread or -1 if not called within tick e.g. shutdown or startup
	 * @param tick
	 * @return true if task is finished and should be removed
	 * @throws Exception
	 */
	boolean tick(int tick) throws Exception;

}
